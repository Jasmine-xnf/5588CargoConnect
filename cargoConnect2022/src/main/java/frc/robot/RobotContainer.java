// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants;
import frc.robot.commands.FlywheelStartCommand;
import frc.robot.commands.OneIndexBallCommand;
import frc.robot.commands.BallIntake;
import frc.robot.commands.BallOuttake;
import frc.robot.commands.Transport;
import frc.robot.commands.DriveDistance;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem= new DriveSubsystem();// declaring new drivesystem object
  private final DriveDistance m_autoCommand = new DriveDistance(m_driveSubsystem, 144, -0.75);
  public static BallSubsystem M_BALL_SUBSYSTEM = new BallSubsystem();

  public static XboxController driverXBox = new XboxController(1);

  private static final int A_BUTTON_XBOX = 1;
  private static final int B_BUTTON_XBOX = 2;
  private static final int X_BUTTON_XBOX = 3;
  private static final int Y_BUTTON_XBOX = 4;
  private static final int LEFT_BUMPER_XBOX = 5;
  private static final int RIGHT_BUMPER_XBOX = 6;
  private static final int BACK_ARROW = 7;
  private static final int START_ARROW = 8;
  private static final int JOYSTICK_RIGHT_CLICK = 10;
  private static final int JOYSTICK_LEFT_CLICK = 9;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_driveSubsystem.setDefaultCommand(
        new RunCommand(() -> m_driveSubsystem.tankDrive(driverXBox.getRawAxis(1), driverXBox.getRawAxis(5)), m_driveSubsystem));
    // ^ Setting the Default Command to m_robotDrive, meaning it will drive as long
    // as nothing else is scheduled
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton flywheelStarButton = new JoystickButton(driverXBox, B_BUTTON_XBOX);
    flywheelStarButton.toggleWhenPressed(new FlywheelStartCommand(M_BALL_SUBSYSTEM));

    JoystickButton oneIndexBallCommandButton = new JoystickButton(driverXBox, A_BUTTON_XBOX);
    oneIndexBallCommandButton.whileHeld(new OneIndexBallCommand(M_BALL_SUBSYSTEM));

    JoystickButton intakeButton = new JoystickButton(driverXBox, X_BUTTON_XBOX);
    intakeButton.whileHeld(new BallIntake(M_BALL_SUBSYSTEM));

    JoystickButton outtakeButton = new JoystickButton(driverXBox, Y_BUTTON_XBOX);
    outtakeButton.whileHeld(new BallOuttake(M_BALL_SUBSYSTEM));

    JoystickButton transportButton = new JoystickButton(driverXBox, LEFT_BUMPER_XBOX);
    transportButton.whileHeld(new Transport(M_BALL_SUBSYSTEM));
    
    JoystickButton driveDistanceButton = new JoystickButton(driverXBox, RIGHT_BUMPER_XBOX);
    driveDistanceButton.whenPressed(new DriveDistance(m_driveSubsystem, 144, -0.75));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
