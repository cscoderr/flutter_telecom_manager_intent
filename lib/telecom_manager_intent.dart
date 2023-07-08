
import 'telecom_manager_intent_platform_interface.dart';

class TelecomManagerIntent {
  Future<String?> getPlatformVersion() {
    return TelecomManagerIntentPlatform.instance.getPlatformVersion();
  }
}
