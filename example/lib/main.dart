import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:telecom_manager_intent/telecom_manager_intent.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _telecomManagerIntentPlugin = TelecomManagerIntent();

  @override
  void initState() {
    super.initState();
  }

  Future<void> defaultDialerAction() async {
    try {
      await _telecomManagerIntentPlugin.defaultDialerAction();
    } on PlatformException catch (e) {
      print(e.toString());
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text('Running '),
              TextButton(
                onPressed: () => defaultDialerAction(),
                child: const Text('Default Dialer'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
