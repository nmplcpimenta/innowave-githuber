# InnoWave GitHuber

A GitHub search app for iOS where one may search for a specific user, see the user's details and list its followers.

## Functionality Description

* Search for GitHub users by their username and/or e-mail
* See a user's details
* See a user's list of followers

## App Screenshots

<p float="left">
  <img src="https://raw.githubusercontent.com/nmplcpimenta/innowave-githuber/dev/app-screenshots/app-ios-phone-ss-splashscreen-thumbnail.png" width="200" alt="Splash screen" style="margin-right:30px" />
  <img src="https://raw.githubusercontent.com/nmplcpimenta/innowave-githuber/dev/app-screenshots/app-ios-phone-ss-searchscreen-thumbnail.png" width="200" alt="Search screen" style="margin-right:30px" /> 
  <img src="https://raw.githubusercontent.com/nmplcpimenta/innowave-githuber/dev/app-screenshots/app-ios-phone-ss-detailsscreen-thumbnail.png" width="200" alt="User details screen"/>
</p>

<p float="left">
  <img src="https://raw.githubusercontent.com/nmplcpimenta/innowave-githuber/dev/app-screenshots/app-ios-tablet-ss-nouser-thumbnail.png" width="300" alt="No user" style="margin-right:30px" />
  <img src="https://raw.githubusercontent.com/nmplcpimenta/innowave-githuber/dev/app-screenshots/app-ios-tablet-ss-searchanddetails-thumbnail.png" width="300" alt="Search and details" />
</p>

## Development

My first question was whether to use Android or iOS. However, since in Android the Kotlin language was preferred, and I don't know the language (yet), I chose iOS Swift.

Next was the app structure. Taking into account the challenge's requirements, I chose to use a Master/Detail architecture for the app. After that, a search bar and a list of results would be needed. Obviously, that would go into the master screen. The detail screen would contain the GitHub user's details and the followers' list.

With this plan in mind, I searched for a tutorial on Swift 4 for master/detail flows. I did this both for architecture purposes and remembering the language. After following the first stage of the tutorial, I remembered the rest of my experience in developing for iOS and Swift, and took off from there.

First of all, I chose SplitViewController as the basic structure of my screen flow. It would take care of iPad and rotation changes for the Master/Detail architecture, and give a bit of a different flavour for each experience.
When I had the core functionalities working, I went on to make sure that all level 2 developer requirements were met. Finally, I worked on the app's design (if it can be called as such), using icons from TheNounProject and Google, and Gimp to tweak whatever I needed, and searching for common user profile designs for iOS apps.

## Authors

* **Nuno Pimenta**
