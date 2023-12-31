import 'telecom_manager_intent_platform_interface.dart';

class TelecomManagerIntent {
  Future<void> defaultDialerAction() {
    return TelecomManagerIntentPlatform.instance.defaultDialer();
  }

  Future<bool> isDefaultDialer() {
    return TelecomManagerIntentPlatform.instance.isDefaultDialer();
  }

  Future<void> defaultSmsAction() {
    return TelecomManagerIntentPlatform.instance.defaultSms();
  }

  Future<bool> isDefaultSms() {
    return TelecomManagerIntentPlatform.instance.isDefaultSms();
  }
}
