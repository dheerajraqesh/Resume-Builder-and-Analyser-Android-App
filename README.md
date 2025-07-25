Resume Builder and Analyser Android App

Overview

Resume Builder and Analyser is an Android application developed using Android Studio to help users create and analyze technical resumes. The app provides a user-friendly interface for building professional resumes with customizable fields and includes analysis features to optimize resumes for technical roles, ensuring compatibility with Applicant Tracking Systems (ATS). Users can input personal details, education, work experience, and skills, then export their resumes as PDFs. The analysis feature evaluates resume content for keyword relevance and formatting, helping users improve their job application materials.

Project Structure

Resume-Builder-and-Analyser-Android-App/
├── app/                    # Main Android app source code
├── .gitignore              # Git ignore file for node_modules, build files, etc.
├── README.md               # Project documentation
├── build.gradle            # Gradle build configuration

Setup Instructions

Follow these steps to set up and run the Resume Builder and Analyser app locally:





Clone the Repository:

git clone https://github.com/dheerajraqesh/Resume-Builder-and-Analyser-Android-App.git
cd Resume-Builder-and-Analyser-Android-App



Install Android Studio:





Download and install Android Studio if not already installed.



Ensure you have the Android SDK and necessary build tools configured.



Open the Project:





Launch Android Studio and select Open an existing project.



Navigate to the cloned repository folder and select it.



Install Dependencies:





Sync the project with Gradle by clicking Sync Project with Gradle Files in Android Studio.



Ensure the iText PDF library is included in the build.gradle file:

implementation 'com.itextpdf:itext7-core:7.2.5'



Run the App:





Connect an Android device or start an emulator in Android Studio.



Click Run to build and deploy the app to the device/emulator.

Technologies Used





Android Studio: Development environment for building the Android application.



Kotlin: Primary programming language for app logic and user interface.



iText PDF: Library for generating PDF resumes from user input.



XML: Used for designing the app’s user interface layouts.



Gradle: Build system for managing dependencies and compiling the app.

Usage





Resume Creation:





Launch the app and navigate to the resume creation section.



Input personal details, education, work experience, and technical skills in the provided fields.



Customize the resume layout using predefined templates.



Export the completed resume as a PDF file using the iText PDF library.



Resume Analysis:





Upload or create a resume within the app.



Use the analysis feature to evaluate the resume for keyword relevance and ATS compatibility.



Receive suggestions for improving content, such as adding technical keywords or refining formatting.

Development Notes





The project follows Android development best practices, with modular code organization in the app/src/main/java directory.



The .gitignore file excludes build artifacts and sensitive files (e.g., local.properties) for secure version control.



For production use, ensure the app is tested on multiple Android API levels (e.g., API 21 and above) to guarantee compatibility.



Future enhancements could include integrating NLP-based analysis (e.g., keyword extraction) or additional export formats like DOCX.

Contributing

Contributions are welcome! To contribute:





Fork the repository.



Create a new branch (git checkout -b feature/your-feature).



Commit your changes (git commit -m 'Add your feature').



Push to the branch (git push origin feature/your-feature).



Open a pull request.

Please report bugs or suggest features via the Issues page.

