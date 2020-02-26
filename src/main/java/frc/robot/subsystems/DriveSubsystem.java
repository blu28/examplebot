/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  double m_max;
  /**
   * Creates a new ExampleSubsystem.
   */
  public DriveSubsystem() {

  }
  public boolean enable() {
    return true;
  }
  public boolean disable() {
    return true;
  }
  public boolean atSetpoint() {
    return true;
  }
  public boolean setMaxOutput(double maxout) {
    this.m_max = maxout;
    return true;
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
