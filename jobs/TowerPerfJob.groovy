pipelineJob("TowerPerf") {
    logRotator {
        daysToKeep(100)
    }

    parameters {
        choiceParam('INVENTORY_FILE', ['conf/inventory-2020-10-14.ini', 'conf/inventory-2020-10-07.ini', 'conf/inventory-gprfc009.ini', 'conf/inventory-gprfc005.ini', 'conf/inventory-gprfc001.ini', 'conf/inventory-cluster.ini', 'conf/inventory-gprfc002.ini'], 'Which inventory file to use?')
        choiceParam('CONFIG_FILE', ['conf/overrides-2020-10-14.yaml', 'conf/overrides-2020-10-07.yaml', 'conf/overrides-gprfc009.yaml', 'conf/overrides-gprfc005.yaml', 'conf/overrides-gprfc001.yaml', 'conf/overrides-cluster.yaml', 'conf/overrides-gprfc002.yaml'], 'Which additional config file to use?')
        textParam('EXPERIMENT_PARAMS', 'EXTRA_SETUP_PARAMS: \'-e gpgcheck=1\'', 'Additional config script YAML')
        booleanParam('RUN_CLEANUP_VMS', true, 'Should we run cleanup VMs stage?')
        booleanParam('RUN_RECREATE_VMS', true, 'Should we run recreate VMs stage?')
        booleanParam('RUN_INSTALL_TOWER', true, 'Should we run Tower installation stage?')
        booleanParam('RUN_MONITORING_SETUP', true, 'Should we run monitoring setup stage?')
        booleanParam('RUN_TEST', true, 'Should we run test stage?')
        booleanParam('RUN_TEST_chatty', true, 'Should we run chatty test?')
        booleanParam('RUN_TEST_overhead', false, 'Should we run chatty test to measure Tower overhead?')
        booleanParam('RUN_TEST_api', true, 'Should we run API test?')
        booleanParam('RUN_TEST_launching', true, 'Should we run launching test that excercises scheduler?')
        stringParam('test_chatty_playbook', 'chatty_tasks.yml', 'Playbook to run: chatty_tasks.yml/chatty_payload.yml')
        stringParam('test_chatty_hosts', '100', 'Number of hosts to use for a test run')
        stringParam('test_chatty_concurency', '10', 'Number of concurrent job runs')
        stringParam('test_chatty_repeat', '5', 'Number of times to run the test')
        stringParam('test_chatty_num_messages', '100', 'Number of mesages test playbook should generate')
        stringParam('test_chatty_message_size', '1024', 'Message size in Bytes')
        stringParam('test_api_benchmark_min_rounds', '10', 'Number of runs of API benchmark tests')
        stringParam('test_launching_hosts', '10', 'Number of hosts to use for a test run')
        stringParam('test_launching_concurency', '100', 'Number of concurrent job runs')
        stringParam('test_launching_repeat', '5', 'Number of times to run the test')
        booleanParam('test_api_populate_db', true, 'Should we run populate DB step?')
    }

    definition {
        cpsScm {
            lightweight(true)
            scm {
                git {
                    remote {
                        name()
                        url('github.com:ansible/towerperf.git')
                        credentials('towerperf_id_rsa')
                    }
                    branch('*/master')
                }
            }
            scriptPath("Jenkinsfile")
        }
    }
}
