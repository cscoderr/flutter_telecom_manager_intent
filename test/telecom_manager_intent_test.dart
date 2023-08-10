import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:telecom_manager_intent/telecom_manager_intent.dart';
import 'package:telecom_manager_intent/telecom_manager_intent_method_channel.dart';
import 'package:telecom_manager_intent/telecom_manager_intent_platform_interface.dart';

class MockTelecomManagerIntentPlatform
    with MockPlatformInterfaceMixin
    implements TelecomManagerIntentPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<void> defaultDialer() {
    // TODO: implement defaultDialer
    throw UnimplementedError();
  }
}

void main() {
  final TelecomManagerIntentPlatform initialPlatform =
      TelecomManagerIntentPlatform.instance;

  test('$MethodChannelTelecomManagerIntent is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTelecomManagerIntent>());
  });

  test('getPlatformVersion', () async {
    TelecomManagerIntent telecomManagerIntentPlugin = TelecomManagerIntent();
    MockTelecomManagerIntentPlatform fakePlatform =
        MockTelecomManagerIntentPlatform();
    TelecomManagerIntentPlatform.instance = fakePlatform;

    expect(await telecomManagerIntentPlugin.getPlatformVersion(), '42');
  });
}
