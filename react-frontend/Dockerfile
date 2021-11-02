FROM node:16.13.0-alpine3.13
WORKDIR /app
COPY package.json ./
RUN npm i
COPY ./ ./
EXPOSE 3000
CMD ["npm", "run", "start"]