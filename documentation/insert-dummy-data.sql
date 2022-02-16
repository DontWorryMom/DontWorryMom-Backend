-- create three users with password 'password'
insert into users(username, encrypted_password) values ('automation-user', '$2a$10$STCXyRNa2M9hIChMsVs7weoSwNbk.piyvHLmsjWGqpnBmWXMVdmA2');
insert into users(username, encrypted_password) values ('william', '$2a$10$STCXyRNa2M9hIChMsVs7weoSwNbk.piyvHLmsjWGqpnBmWXMVdmA2');
insert into users(username, encrypted_password) values ('mikey', '$2a$10$STCXyRNa2M9hIChMsVs7weoSwNbk.piyvHLmsjWGqpnBmWXMVdmA2');
insert into users(username, encrypted_password) values ('darlena', '$2a$10$STCXyRNa2M9hIChMsVs7weoSwNbk.piyvHLmsjWGqpnBmWXMVdmA2');

-- create three devices that belong to automation-user
insert into device(user_id) values ((select user_id from users where username='automation-user'));
insert into device(user_id) values ((select user_id from users where username='automation-user'));
insert into device(user_id) values ((select user_id from users where username='automation-user'));

-- create two notification methods (one email, one text)
with new_notif as (
		insert into notification(user_id) values ((
		select user_id from users where username='automation-user')) returning notification_id)
	insert into email_notification(notification_id, email) 
		values (
			(select min(notification_id) from new_notif), 
			'automation-user-email@gmail.com'
		);
with new_notif as (
		insert into notification(user_id) values ((
		select user_id from users where username='automation-user')) returning notification_id)
	insert into text_notification(notification_id, phone_number) 
		values (
			(select min(notification_id) from new_notif), 
			'800-1234-1234'
		);
-- create past location data 
--  irvine lat and long
--  for automation-user's first device
--  over the last 5 minutes)
insert into past_locations(device_id, location_time, location_lat, location_lon) 
	values (
		(select min(device_id) from device d inner join users u on d.user_id = u.user_id where u.username ='automation-user'),
		(now() - interval '5 minute'),
		33.669,		-- irvine lat and long
		-117.823
	);
insert into past_locations(device_id, location_time, location_lat, location_lon) 
	values (
		(select min(device_id) from device d inner join users u on d.user_id = u.user_id where u.username ='automation-user'),
		(now() - interval '4 minute'),
		33.669,		-- irvine lat and long
		-117.823
	);
insert into past_locations(device_id, location_time, location_lat, location_lon) 
	values (
		(select min(device_id) from device d inner join users u on d.user_id = u.user_id where u.username ='automation-user'),
		(now() - interval '3 minute'),
		33.669,		-- irvine lat and long
		-117.823
	);
insert into past_locations(device_id, location_time, location_lat, location_lon) 
	values (
		(select min(device_id) from device d inner join users u on d.user_id = u.user_id where u.username ='automation-user'),
		(now() - interval '2 minute'),
		33.669,		-- irvine lat and long
		-117.823
	);
insert into past_locations(device_id, location_time, location_lat, location_lon) 
	values (
		(select min(device_id) from device d inner join users u on d.user_id = u.user_id where u.username ='automation-user'),
		(now() - interval '1 minute'),
		33.669,		-- irvine lat and long
		-117.823
	);
	
-- link a device with the notification methods
insert into device_notification_methods (device_id, notification_id) values (
	(select min(device_id) from device d inner join users u on d.user_id = u.user_id where u.username ='automation-user'),
	(select min(notification_id) from notification n inner join users u on n.user_id = u.user_id where u.username ='automation-user')
);