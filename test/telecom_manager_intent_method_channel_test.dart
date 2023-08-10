import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:telecom_manager_intent/telecom_manager_intent_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelTelecomManagerIntent platform =
      MethodChannelTelecomManagerIntent();
  const MethodChannel channel = MethodChannel('telecom_manager_intent');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    // expect(await platform.getPlatformVersion(), '42');
  });
}
