/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.report;

/**
 *
 * @author abidi asma
 */
public class RequestEstimation {

    String request;
    String prc;
    String timeEstimation;

    public RequestEstimation() {

    }

    public RequestEstimation(String request, String prc, String timeEstimation) {
        super();
        this.request = request;
        this.prc = prc;
        this.timeEstimation = timeEstimation;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setPrc(String prc) {
        this.prc = prc;
    }

    public String getPrc() {
        return prc;
    }

    public void setTimeEstimation(String timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public String getTimeEstimation() {
        return timeEstimation;
    }
}
