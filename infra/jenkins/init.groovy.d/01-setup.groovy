import jenkins.model.Jenkins
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import hudson.plugins.git.GitSCM
import hudson.plugins.git.BranchSpec
import hudson.plugins.git.UserRemoteConfig

// Configuration initiale du serveur local et du job depuis Git.
def jenkins = Jenkins.get()
if (!(jenkins.securityRealm instanceof HudsonPrivateSecurityRealm)) {
    def realm = new HudsonPrivateSecurityRealm(false)
    realm.createAccount('admin', new File('/run/secrets/jenkins-admin-password').text.trim())
    jenkins.setSecurityRealm(realm)
    def permissions = new FullControlOnceLoggedInAuthorizationStrategy()
    permissions.setAllowAnonymousRead(false)
    jenkins.setAuthorizationStrategy(permissions)
}
jenkins.setNumExecutors(1)
if (jenkins.getItem('bijouterie') == null) {
    def job = jenkins.createProject(WorkflowJob, 'bijouterie')
    def remote = new UserRemoteConfig('https://github.com/Cescito04/bijouterie-microservices.git', null, null, null)
    def scm = new GitSCM([remote], [new BranchSpec('*/main')], false, [], null, null, [])
    def definition = new CpsScmFlowDefinition(scm, 'Jenkinsfile')
    definition.setLightweight(true)
    job.setDefinition(definition)
    job.save()
}
jenkins.save()
