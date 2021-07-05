package com.sahibinden.suite;

public class TestSuiteDetails {

    private String testClassName;
    private String testSuiteNmae;
    private String testCaseName;
    private String testStatus;
    private String testDescription;

    private long startTime;
    private long elaspsedTime;

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getElaspsedTime() {
        return elaspsedTime;
    }

    public void setElaspsedTime(long elaspsedTime) {
        this.elaspsedTime = elaspsedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestSuiteDetails that = (TestSuiteDetails) o;

        if (testClassName != null ? !testClassName.equals(that.testClassName) : that.testClassName != null)
            return false;
        return testCaseName != null ? testCaseName.equals(that.testCaseName) : that.testCaseName == null;
    }

    @Override
    public int hashCode() {
        int result = testClassName != null ? testClassName.hashCode() : 0;
        result = 31 * result + (testCaseName != null ? testCaseName.hashCode() : 0);
        return result;
    }

    public String getTestSuiteNmae() {
        return testSuiteNmae;
    }

    public void setTestSuiteNmae(String testSuiteNmae) {
        this.testSuiteNmae = testSuiteNmae;
    }
}
