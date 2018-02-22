# Madrid Shops

## Android Advanced practice. V Bootcamp.

### Demo 
<img src= "https://github.com/gomez-fabio/practicaAndroidAvanzado/blob/master/app/src/main/demo.gif?raw=true"> 

## Requirements

1. When starting the App for the first time, if there's Internet connection it will download all information from the Shops / Activities end points.
2. The App will cache everything locally: images, data, etc. Even images of the maps.
3. Cache is never invalidated, so once everything has been saved, set a flag and never ever access to the network again.
4. If there's no Internet connection a message will be shown to the user.
5. While caching the App will show an Activity indicator or other loader. Until you finish caching you don't get to the Main menu.
6. The app will have a main menu screen where we'll add two buttons & a two logos. The buttons takes us to the list of shops or activities
7. The list of Shops or Activities will show in the upper 50% screen a map with one pin for each shop or activity.
8. The list of Shops or Activities will show in the lower 50% screen. Logo to the left, background image taking all the row, shop name in the front. Tapping a row takes us to the detail shop or activity screen.
9. All info should be read from a sqlite database.
10. If you tap on a pin in the map a callout will open with the logo and shop or activity name. Taping the callout takes us to the detail shop or activity screen.
11. The map will be always centered in madrid, showing also the user location.
12. All data is at least in Spanish & English: should cache all and display in Spanish (if that's our phone's language) or English otherwise.
13. Shop and Activity detail screen should show name, description, address, and a map showing the shops's or activity's location without any pin.
