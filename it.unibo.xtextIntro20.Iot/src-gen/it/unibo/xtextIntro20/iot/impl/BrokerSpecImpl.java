/**
 * generated by Xtext 2.18.0.M3
 */
package it.unibo.xtextIntro20.iot.impl;

import it.unibo.xtextIntro20.iot.BrokerSpec;
import it.unibo.xtextIntro20.iot.IotPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Broker Spec</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.unibo.xtextIntro20.iot.impl.BrokerSpecImpl#getBrokerHost <em>Broker Host</em>}</li>
 *   <li>{@link it.unibo.xtextIntro20.iot.impl.BrokerSpecImpl#getBrokerPort <em>Broker Port</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BrokerSpecImpl extends MinimalEObjectImpl.Container implements BrokerSpec
{
  /**
   * The default value of the '{@link #getBrokerHost() <em>Broker Host</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrokerHost()
   * @generated
   * @ordered
   */
  protected static final String BROKER_HOST_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBrokerHost() <em>Broker Host</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrokerHost()
   * @generated
   * @ordered
   */
  protected String brokerHost = BROKER_HOST_EDEFAULT;

  /**
   * The default value of the '{@link #getBrokerPort() <em>Broker Port</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrokerPort()
   * @generated
   * @ordered
   */
  protected static final int BROKER_PORT_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getBrokerPort() <em>Broker Port</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrokerPort()
   * @generated
   * @ordered
   */
  protected int brokerPort = BROKER_PORT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BrokerSpecImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return IotPackage.Literals.BROKER_SPEC;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getBrokerHost()
  {
    return brokerHost;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBrokerHost(String newBrokerHost)
  {
    String oldBrokerHost = brokerHost;
    brokerHost = newBrokerHost;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IotPackage.BROKER_SPEC__BROKER_HOST, oldBrokerHost, brokerHost));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getBrokerPort()
  {
    return brokerPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBrokerPort(int newBrokerPort)
  {
    int oldBrokerPort = brokerPort;
    brokerPort = newBrokerPort;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IotPackage.BROKER_SPEC__BROKER_PORT, oldBrokerPort, brokerPort));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case IotPackage.BROKER_SPEC__BROKER_HOST:
        return getBrokerHost();
      case IotPackage.BROKER_SPEC__BROKER_PORT:
        return getBrokerPort();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case IotPackage.BROKER_SPEC__BROKER_HOST:
        setBrokerHost((String)newValue);
        return;
      case IotPackage.BROKER_SPEC__BROKER_PORT:
        setBrokerPort((Integer)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case IotPackage.BROKER_SPEC__BROKER_HOST:
        setBrokerHost(BROKER_HOST_EDEFAULT);
        return;
      case IotPackage.BROKER_SPEC__BROKER_PORT:
        setBrokerPort(BROKER_PORT_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case IotPackage.BROKER_SPEC__BROKER_HOST:
        return BROKER_HOST_EDEFAULT == null ? brokerHost != null : !BROKER_HOST_EDEFAULT.equals(brokerHost);
      case IotPackage.BROKER_SPEC__BROKER_PORT:
        return brokerPort != BROKER_PORT_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (brokerHost: ");
    result.append(brokerHost);
    result.append(", brokerPort: ");
    result.append(brokerPort);
    result.append(')');
    return result.toString();
  }

} //BrokerSpecImpl