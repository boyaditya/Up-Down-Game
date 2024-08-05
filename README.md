# Up-Down-Game

Up Down Game is a Java-based application that follows the Model-View-ViewModel (MVVM) architectural pattern. This project is designed to demonstrate the use of MVVM in a Java application, incorporating various components such as game logic, user interface, and database interactions.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- MySQL Connector (included in the `lib` directory)
- An IDE such as IntelliJ IDEA or Visual Studio Code

### Database Setup
1. Import the `db_tmd.sql` file into your MySQL database.
2. Update the database connection settings in the `DB.java` file located in the `model` package.


### Building the Project

To compile the project, navigate to the `src` directory and run the following commands:

```
javac -cp lib/forms_rt.jar; model/*.java
javac -cp lib/forms_rt.jar; view/*.java
javac -cp lib/forms_rt.jar; viewmodel/*.java
javac -cp lib/forms_rt.jar; Main.java
```

### Running the Project
To run the project, use the following command:
```
java -cp lib/forms_rt.jar;lib/mysql-connector-j-8.3.0.jar; Main
```

### Authors
- Boy Aditya Rohmaulana

### Acknowledgements

- Background Image (Forest): [Incolgames](https://incolgames.itch.io/forest-pixel-art-parallax)
- Background Image (River): [DeviantArt](https://www.deviantart.com/5ldo0on/art/River-668927586)
- Character Image: [Freepik](https://www.freepik.com/free-vector/flat-design-pixel-art-character-element-set_32390045.htm)
- Rope Image: [ImgBin](https://imgbin.com/png/90d31YyT/vine-rope-png)
- Rock Image: [Pixilart](https://www.pixilart.com/art/rock-32x32-340269d0d0eebdd)
- Land Image: [Patreon](https://www.patreon.com/posts/pixel-practice-55798345)
- Backsound: [YouTube](https://youtu.be/sU77abq6-o4?si=Fd1N46auceSFGqDL)
- Jump Sound Effect: [YouTube](https://youtu.be/561qHylVC_o?si=WdkFHCB0nXBrLRa9)
- Splash Sound Effect: [YouTube](https://youtu.be/bpBLyDZRJDU?si=bGaLM1nMq_mEom2q)
