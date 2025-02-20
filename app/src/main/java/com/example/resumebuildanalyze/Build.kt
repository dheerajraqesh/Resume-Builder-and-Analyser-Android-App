package com.example.resumebuildanalyze

import android.os.Bundle
import android.os.Environment
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.FileOutputStream
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Build : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_build)

        val generateButton = findViewById<MaterialButton>(R.id.generateButton)
        val educationContainer = findViewById<LinearLayout>(R.id.educationContainer)
        val addEducationButton = findViewById<MaterialButton>(R.id.addEducationButton)
        val projectsContainer = findViewById<LinearLayout>(R.id.projectsContainer)
        val addProjectButton = findViewById<MaterialButton>(R.id.addProjectButton)
        val workExperienceContainer = findViewById<LinearLayout>(R.id.workExperienceContainer)
        val addWorkExperienceButton = findViewById<MaterialButton>(R.id.addWorkExperienceButton)
        val achievementsContainer = findViewById<LinearLayout>(R.id.achievementsContainer)
        val addAchievementsButton = findViewById<MaterialButton>(R.id.addAchievementButton)

        addEducationButton.setOnClickListener {
            val newEducationView = layoutInflater.inflate(R.layout.item_education, educationContainer, false)
            educationContainer.addView(newEducationView)
        }
        addProjectButton.setOnClickListener {
            val newProjectView = layoutInflater.inflate(R.layout.item_project, projectsContainer, false)
            projectsContainer.addView(newProjectView)
        }
        addWorkExperienceButton.setOnClickListener {
            val newWorkExperienceView = layoutInflater.inflate(R.layout.item_work_experience, workExperienceContainer, false)
            workExperienceContainer.addView(newWorkExperienceView)
        }
        addAchievementsButton.setOnClickListener {
            val newAchievementView = layoutInflater.inflate(R.layout.item_achievement, achievementsContainer, false)
            achievementsContainer.addView(newAchievementView)
        }

        generateButton.setOnClickListener {
            try {
                val name = findViewById<TextInputEditText>(R.id.nameInput).text.toString()
                val phone = findViewById<TextInputEditText>(R.id.phoneInput).text.toString()
                val email = findViewById<TextInputEditText>(R.id.emailInput).text.toString()

                val linkedinUsername = findViewById<TextInputEditText>(R.id.linkedinUsernameInput).text.toString()
                val linkedinUrl = findViewById<TextInputEditText>(R.id.linkedinUrlInput).text.toString()
                val githubUsername=findViewById<TextInputEditText>(R.id.githubUsernameInput).text.toString()
                val githubUrl = findViewById<TextInputEditText>(R.id.githubUrlInput).text.toString()
                val twitterUsername = findViewById<TextInputEditText>(R.id.twitterUsernameInput).text.toString()
                val twitterUrl = findViewById<TextInputEditText>(R.id.twitterUrlInput).text.toString()

                val district = findViewById<TextInputEditText>(R.id.districtInput).text.toString()
                val state = findViewById<TextInputEditText>(R.id.stateInput).text.toString()
                val pincode = findViewById<TextInputEditText>(R.id.pincodeInput).text.toString()
                val country = findViewById<TextInputEditText>(R.id.countryInput).text.toString()

                val programmingLanguages = findViewById<TextInputEditText>(R.id.programmingLanguagesInput)?.text.toString()
                val webTechnologies = findViewById<TextInputEditText>(R.id.webTechInput)?.text.toString()
                val databaseSystems = findViewById<TextInputEditText>(R.id.databasesInput)?.text.toString()
                val otherToolsAndTechnologies = findViewById<TextInputEditText>(R.id.otherToolsInput)?.text.toString()

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val educationList = mutableListOf<Map<String, String>>()
                for (i in 0 until educationContainer.childCount) {
                    val educationView = educationContainer.getChildAt(i) as? ViewGroup
                    if (educationView != null) {
                        val collegeName = educationView.findViewById<TextInputEditText>(R.id.undergradCollegeInput)?.text.toString()
                        val major = educationView.findViewById<TextInputEditText>(R.id.undergradMajorInput)?.text.toString()
                        val cgpa = educationView.findViewById<TextInputEditText>(R.id.undergradCgpaInput)?.text.toString()
                        val startYear = educationView.findViewById<TextInputEditText>(R.id.undergradStartYearInput)?.text.toString()
                        val endYear = educationView.findViewById<TextInputEditText>(R.id.undergradEndYearInput)?.text.toString()
                        educationList.add(mapOf("collegeName" to collegeName, "degree" to major, "startYear" to startYear, "endYear" to endYear, "cgpa" to cgpa))
                    }
                }
                val educationLatex = generateEducationLatex(educationList)

                val projectList = mutableListOf<Map<String, String>>()
                for (i in 0 until projectsContainer.childCount) {
                    val projectView = projectsContainer.getChildAt(i) as? ViewGroup
                    if (projectView != null) {
                        val domain = projectView.findViewById<TextInputEditText>(R.id.projectDomainInput)?.text.toString()
                        val tools = projectView.findViewById<TextInputEditText>(R.id.projectToolsInput)?.text.toString()
                        val features = projectView.findViewById<TextInputEditText>(R.id.projectFeaturesInput)?.text.toString()
                        projectList.add(mapOf("domain" to domain, "tools" to tools, "features" to features))
                    }
                }
                val projectsLatex = generateProjectsLatex(projectList)

                val experienceList = mutableListOf<Map<String, String>>()
                for (i in 0 until workExperienceContainer.childCount) {
                    val experienceView = workExperienceContainer.getChildAt(i) as? ViewGroup
                    if (experienceView != null) {
                        val companyName = experienceView.findViewById<TextInputEditText>(R.id.companyNameInput)?.text.toString()
                        val companyLink = experienceView.findViewById<TextInputEditText>(R.id.companyLinkInput)?.text.toString()
                        val location = experienceView.findViewById<TextInputEditText>(R.id.locationInput)?.text.toString()
                        val jobTitle = experienceView.findViewById<TextInputEditText>(R.id.positionInput)?.text.toString()
                        val startYear = experienceView.findViewById<TextInputEditText>(R.id.workExperienceStartYearInput)?.text.toString()
                        val endYear = experienceView.findViewById<TextInputEditText>(R.id.workExperienceEndYearInput)?.text.toString()
                        val responsibilities = experienceView.findViewById<TextInputEditText>(R.id.workExperienceDetailsInput)?.text.toString()
                        experienceList.add(mapOf("companyName" to companyName, "companyLink" to companyLink, "location" to location, "jobTitle" to jobTitle, "startYear" to startYear, "endYear" to endYear, "responsibilities" to responsibilities))
                    }
                }
                val experienceLatex = generateExperienceLatex(experienceList)

                val achievementList = mutableListOf<Map<String, String>>()
                for (i in 0 until achievementsContainer.childCount) {
                    val achievementView = achievementsContainer.getChildAt(i) as? ViewGroup
                    if (achievementView != null) {
                        val domain = achievementView.findViewById<TextInputEditText>(R.id.achievementDomainInput)?.text.toString()
                        val description = achievementView.findViewById<TextInputEditText>(R.id.achievementDescriptionInput)?.text.toString()
                        val proofLink = achievementView.findViewById<TextInputEditText>(R.id.achievementLinkInput)?.text.toString()
                        achievementList.add(mapOf("domain" to domain, "description" to description, "proofLink" to proofLink))
                    }
                }
                val achievementsLatex = generateAchievementsLatex(achievementList)

                val latexCode="""
\documentclass[a4paper,11pt]{article}
\usepackage{latexsym}
\usepackage{xcolor}
\usepackage{float}
\usepackage{ragged2e}
\usepackage[empty]{fullpage}
\usepackage{wrapfig}
\usepackage{lipsum}
\usepackage{tabularx}
\usepackage{titlesec}
\usepackage{geometry}
\usepackage{marvosym}
\usepackage{verbatim}
\usepackage{enumitem}
\usepackage{fancyhdr}
\usepackage{multicol}
\usepackage{graphicx}
\usepackage{cfr-lm}
\usepackage[T1]{fontenc}
\usepackage{fontawesome5}

\definecolor{darkblue}{RGB}{0,0,139}

\setlength{\multicolsep}{0pt} 
\pagestyle{fancy}
\fancyhf{} 
\fancyfoot{}
\renewcommand{\headrulewidth}{0pt}
\renewcommand{\footrulewidth}{0pt}
\geometry{left=1.4cm, top=0.8cm, right=1.2cm, bottom=1cm}
\setlength{\footskip}{5pt}

\usepackage[hidelinks]{hyperref}
\hypersetup{
    colorlinks=true,
    linkcolor=darkblue,
    filecolor=darkblue,
    urlcolor=darkblue,
}

\usepackage[most]{tcolorbox}
\tcbset{
    frame code={},
    center title,
    left=0pt,
    right=0pt,
    top=0pt,
    bottom=0pt,
    colback=gray!20,
    colframe=white,
    width=\dimexpr\textwidth\relax,
    enlarge left by=-2mm,
    boxsep=4pt,
    arc=0pt,outer arc=0pt,
}

\urlstyle{same}

\raggedright
\setlength{\tabcolsep}{0in}

\titleformat{\section}{
  \vspace{-4pt}\scshape\raggedright\large
}{}{0em}{}[\color{black}\titlerule \vspace{-7pt}]

\newcommand{\resumeItem}[2]{
  \item{
    \textbf{#1}{\hspace{0.5mm}#2 \vspace{-0.5mm}}
  }
}

\newcommand{\resumePOR}[3]{
\vspace{0.5mm}\item
    \begin{tabular*}{0.97\textwidth}[t]{l@{\extracolsep{\fill}}r}
        \textbf{#1}\hspace{0.3mm}#2 & \textit{\small{#3}} 
    \end{tabular*}
    \vspace{-2mm}
}

\newcommand{\resumeSubheading}[4]{
\vspace{0.5mm}\item
    \begin{tabular*}{0.98\textwidth}[t]{l@{\extracolsep{\fill}}r}
        \textbf{#1} & \textit{\footnotesize{#4}} \\
        \textit{\footnotesize{#3}} &  \footnotesize{#2}\\
    \end{tabular*}
    \vspace{-2.4mm}
}

\newcommand{\resumeProject}[4]{
\vspace{0.5mm}\item
    \begin{tabular*}{0.98\textwidth}[t]{l@{\extracolsep{\fill}}r}
        \textbf{#1} & \textit{\footnotesize{#3}} \\
        \footnotesize{\textit{#2}} & \footnotesize{#4}
    \end{tabular*}
    \vspace{-2.4mm}
}

\newcommand{\resumeSubItem}[2]{\resumeItem{#1}{#2}\vspace{-4pt}}

\renewcommand{\labelitemi}{${'$'}\vcenter{\hbox{\tiny${'$'}\bullet${'$'}}}${'$'}}
\renewcommand{\labelitemii}{${'$'}\vcenter{\hbox{\tiny${'$'}\circ${'$'}}}${'$'}}

\newcommand{\resumeSubHeadingListStart}{\begin{itemize}[leftmargin=*,labelsep=1mm]}
\newcommand{\resumeHeadingSkillStart}{\begin{itemize}[leftmargin=*,itemsep=1.7mm, rightmargin=2ex]}
\newcommand{\resumeItemListStart}{\begin{itemize}[leftmargin=*,labelsep=1mm,itemsep=0.5mm]}

\newcommand{\resumeSubHeadingListEnd}{\end{itemize}\vspace{2mm}}
\newcommand{\resumeHeadingSkillEnd}{\end{itemize}\vspace{-2mm}}
\newcommand{\resumeItemListEnd}{\end{itemize}\vspace{-2mm}}
\newcommand{\cvsection}[1]{%
\vspace{2mm}
\begin{tcolorbox}
    \textbf{\large #1}
\end{tcolorbox}
    \vspace{-4mm}
}

\newcolumntype{L}{>{\raggedright\arraybackslash}X}%
\newcolumntype{R}{>{\raggedleft\arraybackslash}X}%
\newcolumntype{C}{>{\centering\arraybackslash}X}%

\newcommand{\socialicon}[1]{\raisebox{-0.05em}{\resizebox{!}{1em}{#1}}}
\newcommand{\ieeeicon}[1]{\raisebox{-0.3em}{\resizebox{!}{1.3em}{#1}}}


\newcommand{\headerfonti}{\fontfamily{phv}\selectfont} 
\newcommand{\headerfontii}{\fontfamily{ptm}\selectfont}
\newcommand{\headerfontiii}{\fontfamily{ppl}\selectfont} 
\newcommand{\headerfontiv}{\fontfamily{pbk}\selectfont}
\newcommand{\headerfontv}{\fontfamily{pag}\selectfont} 
\newcommand{\headerfontvi}{\fontfamily{cmss}\selectfont} 
\newcommand{\headerfontvii}{\fontfamily{qhv}\selectfont} 
\newcommand{\headerfontviii}{\fontfamily{qpl}\selectfont} 
\newcommand{\headerfontix}{\fontfamily{qtm}\selectfont}
\newcommand{\headerfontx}{\fontfamily{bch}\selectfont} 

\begin{document}
\headerfontiii

\begin{center}
    {\Huge\textbf{$name}}
\end{center}
\vspace{-6mm}

\begin{center}
    \small{
    +91-$phone | \href{mailto: $email}{$email}
    }
\end{center}
\vspace{-6mm}

\begin{center}
    \small{
    \socialicon{\faLinkedin} \href{$linkedinUrl}{$linkedinUsername} | 
    \socialicon{\faGithub} \href{$githubUrl}{$githubUsername} | 
    \socialicon{\faTwitter} \href{$twitterUrl}{$twitterUsername}
    }
\end{center}
\vspace{-6mm}
\begin{center}
    \small{$district, $state - $pincode, $country}
\end{center}

\vspace{-4mm}
\section{\textbf{Objective}}
\vspace{1mm}
\small{
Seeking a challenging position in Data Science / Software Engineering to leverage my expertise in those fields. Aiming to contribute to innovative projects at the intersection of Software Engineering and practical problem-solving in fields such as App/Web development and Data Science.
}
\vspace{-2mm}

$experienceLatex
\vspace{-6mm}

$educationLatex
\vspace{-6mm}

$projectsLatex
\vspace{-6mm}

\section{\textbf{Skills}}
\vspace{-0.4mm}
 \resumeHeadingSkillStart
  \resumeSubItem{Programming Languages:}
    {$programmingLanguages}
  \resumeSubItem{Web Technologies:}
    {$webTechnologies}
  \resumeSubItem{Database Systems:}
    {$databaseSystems}
  \resumeSubItem{Other Tools \& Technologies:}
    {$otherToolsAndTechnologies}
 \resumeHeadingSkillEnd


$achievementsLatex
\vspace{-6mm}
\end{document}
""".trimIndent()
                sendLatexToServer(latexCode)
            } catch (e: Exception) {
                Toast.makeText(this, "Error generating resume: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun generateProjectsLatex(projectList: List<Map<String, String>>): String {
        val latexBuilder = StringBuilder()
        latexBuilder.append("\\section{\\textbf{Projects}}\n")
        latexBuilder.append("\\vspace{-0.4mm}\n")
        latexBuilder.append("\\resumeSubHeadingListStart\n\n")
        for (project in projectList) {
            latexBuilder.append("\\resumeProject\n")
            latexBuilder.append("{${project["domain"]}}{${project["tools"]}}{ }{ }\n")
            latexBuilder.append("\\resumeItemListStart\n")
            latexBuilder.append("\\item ${project["features"]}\n")
            latexBuilder.append("\\resumeItemListEnd\n\n")
        }
        latexBuilder.append("\\resumeSubHeadingListEnd\n")
        return latexBuilder.toString()
    }

    fun generateEducationLatex(educationList: List<Map<String, String>>): String {
        val latexBuilder = StringBuilder()
        latexBuilder.append("\\section{\\textbf{Education}}\n")
        latexBuilder.append("\\vspace{-0.4mm}\n")
        latexBuilder.append("\\resumeSubHeadingListStart\n\n")
        for (education in educationList) {
            latexBuilder.append("\\resumeSubheading\n")
            latexBuilder.append("{${education["collegeName"]}}{${education["location"]}}\n")
            latexBuilder.append("{${education["degree"]}}{${education["startYear"]} - ${education["endYear"]}}{ }\n")
            latexBuilder.append("\\resumeItemListStart\n")
            latexBuilder.append("\\item CGPA: ${education["cgpa"]}\n")
            latexBuilder.append("\\resumeItemListEnd\n\n")
        }
        latexBuilder.append("\\resumeSubHeadingListEnd\n")
        return latexBuilder.toString()
    }

    fun generateExperienceLatex(experienceList: List<Map<String, String>>): String {
        val latexBuilder = StringBuilder()
        latexBuilder.append("\\section{\\textbf{Experience}}\n")
        latexBuilder.append("\\vspace{-0.4mm}\n")
        latexBuilder.append("\\resumeSubHeadingListStart\n\n")
        for (experience in experienceList) {
            latexBuilder.append("\\resumeSubheading\n")
            latexBuilder.append("{${experience["companyName"]} [\\href{${experience["companyLink"]}}{\\faIcon{globe}}]}")
            latexBuilder.append("{${experience["location"]}}\n")
            latexBuilder.append("{${experience["jobTitle"]}}{${experience["startYear"]} - ${experience["endYear"]}}\n")
            latexBuilder.append("\\resumeItemListStart\n")
            val responsibilities = experience["responsibilities"]?.split("\n") ?: emptyList()
            for (responsibility in responsibilities) {
                latexBuilder.append("\\item $responsibility\n")
            }
            latexBuilder.append("\\resumeItemListEnd\n\n")
        }
        latexBuilder.append("\\resumeSubHeadingListEnd\n")
        return latexBuilder.toString()
    }

    fun generateAchievementsLatex(achievementList: List<Map<String, String>>): String {
        val latexBuilder = StringBuilder()
        latexBuilder.append("\\section{\\textbf{Achievements}}\n")
        latexBuilder.append("\\vspace{-0.4mm}\n")
        latexBuilder.append("\\resumeSubHeadingListStart\n\n")
        for (achievement in achievementList) {
            latexBuilder.append("\\resumePOR\n")
            latexBuilder.append("{${achievement["domain"]}}\n")
            latexBuilder.append("{${achievement["description"]}}\n")
            latexBuilder.append("{[\\href{${achievement["proofLink"]}}{\\textcolor{darkblue}{\\faIcon{globe}}}]}\n\n")
        }
        latexBuilder.append("\\resumeSubHeadingListEnd\n")
        return latexBuilder.toString()
    }

    fun sendLatexToServer(latexCode: String) {
        val trustAllCertificates = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })
        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCertificates, java.security.SecureRandom())
        }
        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://172.20.10.3:3000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LatexApiService::class.java)
        val call = service.compileLatex(LatexRequest(latex_code = latexCode))

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val downloadPdfFile = File(downloadDir, "resume.pdf")

                    response.body()?.byteStream()?.use { input ->
                        FileOutputStream(downloadPdfFile).use { output ->
                            input.copyTo(output)
                        }
                    }

                    Toast.makeText(this@Build, "PDF saved at Downloads", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Build, "Failed to compile LaTeX", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@Build, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
