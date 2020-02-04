/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    printAllStatusFiles();

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  private void printStatusFile(String filename, Boolean isDeploy, int rowIndex, int colIndex, int widthIndex) {
    byte[] buffer = new byte[1024];
    InputStream statusfile;
    try {
      if (isDeploy) {
        if (RobotBase.isSimulation()) {
          statusfile = new BufferedInputStream(
              new FileInputStream(Filesystem.getLaunchDirectory() + "/src/main/deploy/" + filename));
        } else {
          statusfile = new BufferedInputStream(new FileInputStream(Filesystem.getDeployDirectory() + "/" + filename));
        }
      } else {
        statusfile = getClass().getResourceAsStream("/" + filename);
      }
      System.out.print((filename + ": ").replace(".txt", ""));
      try {
        for (int length = 0; (length = statusfile.read(buffer)) != -1;) {
          String buf = new String(buffer).replaceAll("\\s"," ");
          String tfn = filename.replace(".txt", "");
          String fn = tfn.substring(0,1).toUpperCase() + tfn.substring(1);
          System.out.write(buffer, 0, length);
          SmartDashboard.putString(fn, buf);
          Shuffleboard.getTab("Status").add(fn, buf).withPosition(colIndex, rowIndex).withSize(widthIndex, 1);
        }
      } finally {
        System.out.println();
        statusfile.close();
      }
    } catch (Exception e) {
      System.out.println("Unable to find file.");
      System.out.println(e.getMessage());
    }
  }

  private void printAllStatusFiles() {
    // Print the Splash Screen
    System.out.println("==============================================");
    System.out.println("Starting robotInit for Tough Techs");
    printStatusFile("deployhost.txt", true, 0, 2, 1);
    printStatusFile("deploytime.txt", true, 0, 3, 2);
    printStatusFile("buildtime.txt", false, 0, 0, 2);
    printStatusFile("branch.txt", false, 0, 5, 1);
    printStatusFile("commit.txt", false, 1, 0, 10);
    printStatusFile("changes.txt", false, 2, 0, 10);
    printStatusFile("remote.txt", false, 3, 0, 10);
    System.out.println("============================================");
  }
}
