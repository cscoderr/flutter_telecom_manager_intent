import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'telecom_manager_intent_platform_interface.dart';

/// An implementation of [TelecomManagerIntentPlatform] that uses method channels.
class MethodChannelTelecomManagerIntent extends TelecomManagerIntentPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('telecom_manager_intent');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
