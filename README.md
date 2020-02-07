This repo contains a copy of the Examplebot with modifications made to track and log information about the 
actual code deployed on the robot. We found that with different mentors and students deploying to different
robots on different days we often had code running on the robot that was different from what we thought it 
was. 

This code uses both the deploy and resources directories to track the info. Files that are in the resources 
directory are packaged into the jar file, so we keep information about the actual build in it. Files in the 
deploy directory are copied as is into the deploy directory on the robot, so we put information about when it
was deployed and what computer was used to deploy it there. 

To use this feature all you have to do is add the file "splash.gradle" to the project root directory, and the
file "Splash.java" to the java/frc/robot directory. Once that is done add this line to the existing
build.gradle file after the "plugins" section:

apply from: "splash.gradle"

And this line at the top of the Robot.java file:

import frc.robot.Splash;

And this line to the robotInit() method in the Robot.java file:

    Splash.printAllStatusFiles();

In addition, the splash.gradle file includes code to load the Oblarg.Oblog logging method and the halsim_ds_socket
plugin for the desktop simulator so it can use the regular drive station in testing. The halsim plugin doesn't hurt 
anything if you leave it in and don't use it (except to have it appear as an option when you run the simulator), 
but to use the Oblarg.Oblog feature you also need to add this line to the top of the RobotContainer.java file:

import io.github.oblarg.oblog.Logger;

and this line to the RobotContainer() method in the same file:

Logger.configureLoggingAndConfig(this, false);

Further information on using the Oblog feature can be found at https://oblog-docs.readthedocs.io/en/latest/

Note that the version of Oblog is hard coded to prevent an update from breaking things at an inopportune moment.
You can manually update it to ther latest version or change the it to always use the latest.
