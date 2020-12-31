/**
 * A class for, performing <pre> GET </pre> requests for <strong>ALL</strong> users
 */
export default class CommonGetRequestsExecutor {

    /**
     * This method searches <strong>all public</strong> user's agendas by specified username.
     *
     * @param username - specified username.
     */
    executeSearchSomeOnesAgendaGetRequest(username) {
        window.location.href = '/search?username=' + username
    }
}