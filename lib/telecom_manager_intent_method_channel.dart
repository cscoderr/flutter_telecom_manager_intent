import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'telecom_manager_intent_platform_interface.dart';

/// An implementation of [TelecomManagerIntentPlatform] that uses method channels.
class MethodChannelTelecomManagerIntent extends TelecomManagerIntentPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('telecom_manager_intent');

  @override
  Future<void> defaultDialer() async {
    return methodChannel.invokeMethod('defaultDialer');
  }

  @override
  Future<bool> isDefaultDialer() async {
    final isDefaultDialer = await methodChannel.invokeMethod('isDefaultDialer');
    return isDefaultDialer;
  }

  @override
  Future<void> defaultSms() {
    return methodChannel.invokeMethod('defaultSms');
  }

  @override
  Future<bool> isDefaultSms() async {
    final isDefaultSms = await methodChannel.invokeMethod('isDefaultSms');
    return isDefaultSms;
  }
}
