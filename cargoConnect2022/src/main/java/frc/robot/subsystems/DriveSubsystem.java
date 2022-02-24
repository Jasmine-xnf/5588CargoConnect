/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  public static DifferentialDrive m_drive;

  private CANSparkMax frontLeftMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax frontRightMotor = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax backLeftMotor = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax backRightMotor = new CANSparkMax(4, MotorType.kBrushless);

  private RelativeEncoder m_frontLeftEncoder = frontLeftMotor.getEncoder();
  private RelativeEncoder m_frontRightEncoder = frontRightMotor.getEncoder();
  private RelativeEncoder m_backRightEncoder = backRightMotor.getEncoder();
  private RelativeEncoder m_backLeftEncoder = backLeftMotor.getEncoder();
  private double encoderPCF = 2;
  

  public DriveSubsystem() {
    frontLeftMotor.setInverted(true);
    frontRightMotor.setInverted(false);
    backLeftMotor.setInverted(true);
    backRightMotor.setInverted(false);
    m_frontLeftEncoder.setPositionConversionFactor(encoderPCF);
    m_frontRightEncoder.setPositionConversionFactor(encoderPCF);
    m_backRightEncoder.setPositionConversionFactor(encoderPCF);
    m_backLeftEncoder.setPositionConversionFactor(encoderPCF);

    // ^ FIX: Making sure none of the motors are inverted, change when we figure out
    // WTH is up with the motors lol

    frontLeftMotor.setSmartCurrentLimit(80);
    frontRightMotor.setSmartCurrentLimit(80);
    backLeftMotor.setSmartCurrentLimit(80);
    backRightMotor.setSmartCurrentLimit(80);

    backLeftMotor.follow(frontLeftMotor);
    backRightMotor.follow(frontRightMotor);

    // ???? Configure encoders here

    m_drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

    // ((Object) m_drive).setRightSideInverted(false);
    m_drive.setMaxOutput(.80);
  }

  public void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed * Constants.k, rotation);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    // May need invert left
    m_drive.tankDrive(leftSpeed * Constants.k, rightSpeed * Constants.k);
  }

  @Override
  public void periodic() {
    m_drive.feedWatchdog(); // check this
  }

  public void resetEncoders() {
    m_frontLeftEncoder.setPosition(0.0);
    m_frontRightEncoder.setPosition(0.0);
    m_backLeftEncoder.setPosition(0.0);
    m_backRightEncoder.setPosition(0.0);
  }

  public double getMeanEncoderDistance() {
    // currently report leaders only
    return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0;
  }

  public double getLeftEncoderDistance() {
    // currently report leader only
    return m_frontLeftEncoder.getPosition();
  }

  public double getRightEncoderDistance() {
    // currently report leader only
    return m_frontRightEncoder.getPosition();
  }

  // May want to try this rather than multiplying by constant scale everywhere
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

}
