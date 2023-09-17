import 'telecom_manager_intent_platform_interface.dart';

class TelecomManagerIntent {
  Future<void> defaultDialerAction() {
    return TelecomManagerIntentPlatform.instance.defaultDialer();
  }

  Future<void> isDefaultDialer() {
    return TelecomManagerIntentPlatform.instance.isDefaultDialer();
  }
}
