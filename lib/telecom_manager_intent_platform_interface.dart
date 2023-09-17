import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'telecom_manager_intent_method_channel.dart';

abstract class TelecomManagerIntentPlatform extends PlatformInterface {
  /// Constructs a TelecomManagerIntentPlatform.
  TelecomManagerIntentPlatform() : super(token: _token);

  static final Object _token = Object();

  static TelecomManagerIntentPlatform _instance =
      MethodChannelTelecomManagerIntent();

  /// The default instance of [TelecomManagerIntentPlatform] to use.
  ///
  /// Defaults to [MethodChannelTelecomManagerIntent].
  static TelecomManagerIntentPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [TelecomManagerIntentPlatform] when
  /// they register themselves.
  static set instance(TelecomManagerIntentPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> defaultDialer() {
    throw UnimplementedError('defaultDailer() has not been implemented.');
  }

  Future<void> isDefaultDialer() {
    throw UnimplementedError('isDefaultDialer() has not been implemented.');
  }
}
