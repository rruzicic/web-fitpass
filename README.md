# FitPass
Students project for web programming course.

## Running the project
To run the app locally on your machine please do the folowing:
1. Clone the repo `git clone https://github.com/rruzicic/web-fitpass.git`
2. Navigate to web-fitpass folder - `cd web-fitpass/`
3. Build docker image - `docker build -t fitpass .`
4. Run docker container - `docker run -d -p 8080:8080 fitpass`

If you want to make changes to the app run `mvn clean install` in the projects directory and then copy the `FitPass-0.0.1-SNAPSHOT.war` file from the `FitPass/target/` directory and paste it in the `web-fitpass/` folder. Rename the file to `FitPass.war` and then build the docker image and run the docker container.
*Maybe i'll get around and make this process a lot easier.*