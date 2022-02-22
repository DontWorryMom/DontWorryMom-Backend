from datetime import datetime
import requests
import time

def print_header_string(header):
    print(f" {header} ".center(50,'-'))

def input_with_validation(prompt: str, validation) -> str:
    while True:
        ans = input(prompt)
        if validation(ans):
            return ans

def get_inputs():
    username = input_with_validation("give a username\n", lambda x: len(x)>0)
    email = input_with_validation("give an email address\n", lambda x: len(x)>0)
    phone = input_with_validation("give a number in format +1XXXXXXXXXX\n", lambda x: len(x)==12 and x[0]=='+' and x[1]=='1')
    return (username, email, phone)


BASE_PATH = "http://localhost:8080"

'''
    login to the base
'''
def login(session, username, password):
    response = session.post(BASE_PATH+'/login', data={'username':username, 'password':password})
    return response

'''
    Sends a request to the requested path
'''
def send_req(session, path, method, body):
    return session.post(BASE_PATH+path, json=body)

'''
    Sends multiple requests
    requests is a list of lists with format [[path, method, body, cookies], ...]
'''
def send_requests(requests):
    return [send_req(*r) for r in requests]


if __name__ == "__main__":
    input_username, input_email, input_number = get_inputs()
    session = requests.Session()
    response = session.get(BASE_PATH)

    # CREATE USERS
    print_header_string("CREATING USER")
    create_user_responses = send_requests([
        [session, "/users", "POST", {"username":input_username, "password":"password"}],
    ])
    user = create_user_responses[0].json()['results']
    user_id = user['userId']

    # LOGIN TO THE FIRST CREATED USER
    print_header_string(f"LOGGING INTO USER")
    login(session, user['username'], 'password')
    cookies = session.cookies.get_dict()

    # CREATE THREE DEVICES THAT BELONG TO THE USER
    print_header_string("CREATING DEVICES")
    create_device_responses = send_requests([
        [session, "/devices", "POST", {"userId":user_id}],
        [session, "/devices", "POST", {"userId":user_id}],
        [session, "/devices", "POST", {"userId":user_id}],
    ])
    device = create_device_responses[0].json()['results']
    device_id = device['deviceId']

    # CREATE TWO NOTIFICATION METHODS
    print_header_string("CREATING NOTIFICATION METHODS")
    create_notification_responses = send_requests([
        [session, f"/notifications/userId/{user_id}", "POST", {"type":"TEXT_NOTIFICATION","phoneNumber": input_number}],
        [session, f"/notifications/userId/{user_id}", "POST", {"type":"EMAIL_NOTIFICATION","email": input_email}],
    ])
    text_notification = create_notification_responses[0].json()['results']
    email_notification = create_notification_responses[1].json()['results']

	# CREATE 5 LOCATION DATA POINTS
    print_header_string("CREATING LOCATION DATA")
    for i in range(5):
        # send 5 locations at 1 second apart
        create_location_responses = send_requests([
            [session, f"/locations/deviceId/{device_id}", "POST", {"locationLat": 33.669445,"locationLon": -119.4179,"maxAcceleration": 9.7}]
        ])
        time.sleep(1.0)
    
    # LINK A DEVICE AND A NOTIFICATION METHOD
    print_header_string("LINKING LOCATION DATA")
    create_notification_link_responses = send_requests([
        [session, f"/deviceNotificationMethods", "POST", {"deviceId": device["deviceId"], "notificationId": text_notification["notificationId"]}],
        [session, f"/deviceNotificationMethods", "POST", {"deviceId": device["deviceId"], "notificationId": email_notification["notificationId"]}]
    ])

    # CREATE A CRASH
    print_header_string("CREATING A CRASH")
    create_location_responses = send_requests([
        [session, f"/locations/deviceId/{device_id}", "POST", {"locationLat": 33.669445,"locationLon": -119.4179,"maxAcceleration": 9.9}]
    ])

    # FINISHED
    print_header_string("FINISHED CREATING DATA")
