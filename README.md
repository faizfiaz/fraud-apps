# Fraud Apps

Tech Highlight
#Kotlin,
#MVVM,
#Usecase pattern,
#Navigation Component,
#Data Binding,
#Dagger,
#SQLite,
#Retrofit,

Class and Method definition

1. Home
    for dashboard to see all data about report and fraud
    Code flow:
    UI ->
    ViewModel Trigger Get Data Report ->
    Usecases handle check if lastupdate is not empty and < 60sec will be get in local db ->
    if not get from API and save to db -> After done get fraud list and save to db ->
    total loss and count for report

2. Add Report
    for add report for account bank or phone number
    Code flow:
    UI ->
    User Interact and input data ->
    User click submit ->
    ViewModel send data to usecase ->
    usecase send data to server using repository ->
    give response to viewmodel -> if success back to home and update

3. Fraud List
    for page to see all data about fraud in one report
    Code flow:
    UI ->
    ViewModel Trigger Get Data Fraud ->
    Usecases will be get data fraud in local db ->
    give response to viewmodel ->
    show list to user

4. Add Fraud
    for add fraud for account bank or phone number
    Code flow:
    UI ->
    User Interact and input/edit data ->
    User click edit ->
    ViewModel send data to usecase ->
    usecase send data to server using repository ->
    give response to viewmodel -> if success back to fraud list page and update
    back to home will update the value too


Instalation

1. Add Keystore in drive to folder keystore
2. Create folder env in root/app
3. Add env properties in drive to folder env
4. Rebuild

link gdrive : https://drive.google.com/drive/folders/1zKYBsWeJVUaPSWa7Mm7PyenTHTVOrH91?usp=sharing

