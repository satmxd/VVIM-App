# VVIM-App
QR-scan-based Android app for trees along with interactive quiz & facts for VVIM

## Features
- **Tree database**
  - Curated database of 80+ trees/plants in our school with detailed information about each of them along with images
  - Scraped from .pdf to .txt using Python
  - Converted to SQLite database for easier Android App compatibility
- **QR Generator**
  - Prompt-based QR generator made using Python
  - Libraries used- customtkinter, qrcode, Pillow, etc.
  - Editable parameters like QR size, pixel width, embedded logo, etc.
  - Used to create QR codes of trees which give information about them when scanned using the App.
- **Android App**
  - Easy-to-use Android app made using Android Studio and Java
  - Gives detailed information on trees including their binomial nomenclature, medicinal values, common names, etc.
  - Interactive quizzes and facts to increase the learning outcomes of users.
  - 3D View of the school with the location of every tree present for easier access.
  - Gallery of different trees across the school.

## Screenshots
| Splash Screen | Home page | Information | Quiz | Quiz Response |
|:-:|:-:|:-:|:-:|:-:|
| ![First](/.github/assets/splashscreen.png?raw=true) | ![Sec](/.github/assets/homepage.png?raw=true) | ![thr](/.github/assets/infopage.png?raw=true) | ![fourth](/.github/assets/quizpage.png?raw=true) | ![fifth](/.github/assets/quizresponse.png?raw=true) |
