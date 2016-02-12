# Rotten Tomatoes Android App

Download Android Studio.
Install SDK and System Image for Android Lollipop (5.0.1, API Level 21).

In order for the app to communicate with the server, the server must be running!

To run the server:

Install Node.js:
  - Windows: Install from https://nodejs.org/en/

cd into the swagger-server directory, then:
Run the command:
  `curl https://raw.githubusercontent.com/creationix/nvm/v0.30.2/install.sh | bash`
  `nvm install stable`
  `nvm alias default stable`
  `npm install`

Then to start the server:
  `npm start`
