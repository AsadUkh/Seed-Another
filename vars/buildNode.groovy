def call(body)
 {
        def pipelineParams= [:]
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = pipelineParams
        body()
        if (${pipelineParams.deployTo} == 'node') 
            {
	        pipeline
            {
                agent {
			docker {
				label 'docker-agent'
					}
				}
				
                stages 
                {
                    stage('Test') 
                        {
                            steps 
                            {
                                sh 'node --version'
                                sh 'svn --version'
                            }
                        }
                }
            }
	
	    }
 }
