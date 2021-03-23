job('Seed Job') {
  label(name='swarm')
  wrappers {
    preBuildCleanup()
  }
  scm {
    git {
      branch('master')
      remote {
        name('upstream')
        // replace this with whever you put this repo
        url('https://github.com/IshanSinghal/testJenkinsJOBDSL.git')
      }
    }
  }
  properties {
    pipelineTriggers {
      triggers {
        pollSCM {
          // we will poll 5 times an hour for changes
          scmpoll_spec('H/5 * * * *')
        }
      }
    }
  }
  steps {
    // running tests first prevents half deployments which could break
    gradle 'clean test'
    dsl {
      // any job ending in Job.groovy will be deployed
      external 'jobs/**/*Job.groovy'
    }
  }
  publishers {
    publishHtml {
      report('build/reports/tests/') {
        reportName('Grade Test Results')
      }
    }
  }
}

// This accepts changes in the script approval section of 'Manage Jenkins'.  You may or may not want this.
org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval.get().with { approval ->
  approval.preapproveAll()
  approval.save()
}
