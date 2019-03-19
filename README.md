

# Requirements

* Java
* Maven
* Node
* Yarn
* Vue CLI

# IDE Requirements

* IntelliJ
  * Lombok plugin
  
# Vue CLI

```$bash
npm uninstall -g vue-cli;
npm install -g @vue/cli@latest;
```

 # Storybook
 
 ```
 yarn storybook
  ```
  
Add storybook files to: `src/main/resources/vue/stories/*`.

# Setup

## Windows : (Tested on Windows 10.1803 [Build 17134.523] using JetBrains IntelliJ Idea Ultimate IDE)
 1. Create Workspace: 
 2. git clone https://github.com/jjzabkar/hackathon-streaming.git
 3. download java - http://jdk.java.net/java-se-ri/11
 4. download node - https://nodejs.org/en/
 5. download Yarn - https://yarnpkg.com/en/
 6. Extract Java to common folder / PATH available folder (No Additional Install Process required)
 7. Install Node
 8. Install Yarn
 9. node install -global vue-cli (validation needed on this command as i don't have it recorded.)
 10. Navigate to $\src\main\resources\ 
 11. yarn install (installs all the prereques that are ref'd in the package.config file)
 12. yarn serve (runs the frontend vue app [correct me if i'm wrong])
