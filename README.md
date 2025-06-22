Overview
This Android application helps educational institutions manage student attendance by:

Importing student data from Excel files

Marking daily attendance

Tracking absentees

Generating reports

Features
Excel Import: Bulk import student data from .xlsx files

Attendance Tracking: Mark students as present/absent with a simple interface

Absentee Management: View and export lists of absent students

Room Database: Local storage for student and attendance records

RecyclerView: Efficient display of student lists

Technical Specifications
Minimum SDK: API 21 (Lollipop)

Architecture: MVVM (Model-View-ViewModel)

Database: Room Persistence Library

Excel Processing: Apache POI


Implementation Guide
1. Setup Project
Create new Android project in Android Studio

Select Java as language

Minimum SDK: API 21+

Use Empty Activity template

2. Database Implementation
Create Entity classes (Student.java, Absence.java)

Create DAO interface (AppDao.java)

Create Room Database class (AppDatabase.java)

3. Excel Import Functionality
Add file picker integration

Implement Apache POI for Excel parsing

Map Excel columns to Student fields

Insert parsed data into Room database

4. Attendance Screen
RecyclerView with student list

Each row shows:

Serial number

Student name

Roll number

Absent toggle

Highlight currently focused student

Bottom buttons for quick Present/Absent marking

5. Absentee Recording
Create Absence records when marking absent

Store with current timestamp

Associate with student ID

6. Absentee Reporting
Query Absence table by date

Display in dialog/activity

Option to export as Excel/PDF

Usage Flow
Import student data from Excel

Open attendance screen

Mark students present/absent

View absentees report

Export reports as needed

Screens
Main Activity: Import and manage students

Attendance Screen: Mark daily attendance

Absentee Report: View absent students

Future Enhancements
Multi-day attendance tracking

Statistical reports

Cloud sync capability

PDF export functionality
