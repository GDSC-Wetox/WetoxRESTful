<div align="center" >
  
<img width="150" src="https://github.com/GDSC-Wetox/Wetox-iOS/assets/99120199/f702a1a8-f63e-4f11-839e-0eb8cfcdd6cc">

# Wetox
**“Our detox mate, Wetox”** <br/>
GDSC-Hongik project <br/><br/>
iOS service that tracks my screen time by category and usage duration, allowing me to share this information with friends to facilitate a digital detox together <br/>
</div></div></div>

## 🥅 Goal
"Ensure healthy lives and promote well-being for all at all ages" as part of the UN's Sustainable Development Goals
- Encourage healthy smartphone use by sharing screen time, fostering friendly competition, and providing positive feedback
- Motivate and stimulate healthy smartphone habits through a reward system of badges 
- Protect mental health from the threats posed by smartphone addiction


## ⌨️ How to run
1. Clone the WetoxRESTful repository to the appropriate location and enter the directory.
   ```bash
   # if you are using HTTPS to clone
   git clone https://github.com/GDSC-Wetox/WetoxRESTful.git

   # or...
   
   # if you are using SSH to clone
   git clone git@github.com:GDSC-Wetox/WetoxRESTful.git
   ```
   ```bash
   cd WetoxRESTful
   ```
2. Make sure you are at the branch named `release`
   ```bash
   git switch release
   ```
3. Build projects using gradle.
   ```bash
   ./gradlew clean build
   ```
5. The request to the API server is forwarded to 8080 ports, and the request for AI-generated images is forwarded to 5000 ports. The following is an example of a NGINX setup.
   ```
   server {
       listen 80;
       server_name api.wetox.dev;
    
       location / {
           proxy_pass http://localhost:8080;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
    
       location /ai {
           proxy_pass http://localhost:5000;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
   }
   ```
7. Check the environment variables, database settings, and security settings.]
   ```
   export GOOGLE_PROJECT_ID=<Google project ID here>
   export GOOGLE_APPLICATION_CREDENTIALS=<Google Application crendential file path here>
   export FIREBASE_CREDENTIALS=<Firebase credential file path here>
   export MY_SQL_DEMO=<MySQL URL here>
   export MY_SQL_DEMO_USER=<MySQL username here>
   export MY_SQL_DEMO_PASSWD=<MySQL password here>
   ```
9. Run the built project with the following command.
   ```bash
   nohup java -jar <WetoxRESTful application you built> &
   ```

## 💡 Features 
- **Screen Time Sharing**
    - Share Daily/Weekly screen time for yourself and friends <br>
- **Traffic Light Feature**
    - Available Time = 24 hours - Screen Time
    - A lot of available time is green, surpassing half is orange, and below a quarter is red.
    -The less screen time, the more time spent off the smartphone, providing intuitive and positive feedback on available time <br>
- **Detailed Statistics**
    - Track total usage time and time spent in each category
    - Understand the distribution across categories <br>
- **Badges**
    - Personalized rewards through AI
    - Analyze apps or categories with significant usage changes to provide personalized rewards 

## Architecture
![image](https://github.com/GDSC-Wetox/WetoxRESTful/assets/70203010/7adbd2da-eee4-420b-815a-c6a84db651ec)

## :sparkles: Skills & Tech Stack
|Category|Item|
|:---:|---|
|**Environment**|IntelliJ IDEA|
|**Framework**|Spring Boot, Spring Security|
|**Library**|JJWT, Firebase SDK, Google Cloud SDK|
|**Version Control**|Git, GitHub|
|**Communication**|Notion, Discord|

## 🫂 Developers

|손지석|허혜인|
|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/70203010?v=4" width="190">|<img src="https://avatars.githubusercontent.com/u/128613248?s=400&v=4" width="190">
|[Jiseok Son](https://github.com/jiseokson)|[Hyein Heo](https://github.com/hye-inA)|
|<p align="left">- Authorization, Authentication<br>- Screentime feature<br>- Badge feature|<p align="left">- Friendship feature<br>- Badge feature|

### Special Thanks to 
|Designer 김보영|
|:-:|
|<p align="left">- Logo design <br>- MainView, ScreenInputView design|
