package model.pojos.graphhopper;

public class RouteOptimizationResponse {

    public String job_id;
    public String status;
    public int waiting_time_in_queue;
    public int processing_time;
    public Solution solution;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWaiting_time_in_queue() {
        return waiting_time_in_queue;
    }

    public void setWaiting_time_in_queue(int waiting_time_in_queue) {
        this.waiting_time_in_queue = waiting_time_in_queue;
    }

    public int getProcessing_time() {
        return processing_time;
    }

    public void setProcessing_time(int processing_time) {
        this.processing_time = processing_time;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
