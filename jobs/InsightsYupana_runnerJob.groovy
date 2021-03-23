pipelineJob("InsightsYupana_runner") {
    logRotator {
        daysToKeep(100)
    }

    properties {
        disableConcurrentBuilds()
    }

    parameters {
        booleanParam('TEST_JUST_RESEND', false, 'Are we supposed to just resend previously sent data?')
        stringParam('TEST_TARBALLS_COUNT', '5', 'How many tarballs to prepare')
        stringParam('TEST_SLICES_COUNT', '2', 'How many slices in each tarball to prepare')
        stringParam('TEST_HOSTS_COUNT', '1000', 'How many hosts in each slice to prepareNumber of Satellite to simulate per run')
        stringParam('TEST_HOSTS_TEMPLATE', 'inventory_ingress_yupana_template.json.j2', 'Template for generating hosts')
        stringParam('TEST_HOSTS_PACKAGES', '500', 'Number of packages in the hosts package profile if tempate supports it')
        stringParam('TEST_TARBALLS_CONTENT', 'default', 'If set to "default", nothing special, but if set to "a,b,c,d,e" (i.e. 5 integers delimited by coma), use smqe-tools tarball generator with "--tarballs-count TEST_TARBALLS_COUNT --physicalrh-count a --virtualrh-count b --hypervisor-count c --guests-per-hyper-count d --cloud-count e" parameters')
        booleanParam('GOLDEN', false, 'Should result of this test run be marked as CPT compatible? This should be only set to true if you are automatic builder job.')
    }

    definition {
        cpsScm {
            lightweight(true)
            scm {
                git {
                    remote {
                        name()
                        url('https://gitlab.cee.redhat.com/redhat-performance/insights-perf.git')
                    }
                    branch('*/master')
                }
            }
            scriptPath("yupana/Jenkinsfile")
        }
    }
}
