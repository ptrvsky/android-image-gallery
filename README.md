# Android Image Gallery
Android application that shows images from selected directory in various view styles. Application let user open image in full view, share it or set as wallpaper.

## Functional requirments
- User should be able to choose directory
- User should be able to choose gallery style (grid view, list view)
- User should be able to set image as wallpaper 
- User should be able to share image with other applications
- User should be able to change gallery style with button in action bar menu
- User should be able to slide between images in image full view
- User should be able to check image details and EXIF informations
- Application should open settings if gallery style or directory have not been set before
- Application should save user preferences (gallery style, directory) and use them during startup

## Non-functional requirments
- Application should be created using Fragments and only one Activity
- Application should use Shared Preferences
- Image gallery should be created using Recycler View
- Gallery style choose should be implemented as spinner
- Image details and EXIF informations should be displayed in two Fragments using View Pager

## Screenshots
<img src="https://i.imgur.com/pxa5dew.png" alt="Grid View" width="170"/> <img src="https://i.imgur.com/4bszyJs.png" alt="List View" width="170"/> <img src="https://i.imgur.com/QEo7YkZ.png" alt="Settings" width="170"/> <img src="https://i.imgur.com/7ImfAuj.png" alt="Full View" width="170"/> <img src="https://i.imgur.com/ckkagfm.png" alt="Details" width="170"/>
