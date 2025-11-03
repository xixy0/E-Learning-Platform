import React, { useEffect, useState } from 'react'
import toast from 'react-hot-toast';

function SubmissionReports() {

    const [submissions, setSubmissions] = useState([])
    useEffect(() => {
        fetchSubmissions();
    })

    const fetchSubmissions = async () => {
        try {
            const response = await api.get("/users/getSubmissions");
            setSubmissions(response.data);

        } catch (error) {
            toast.error(
                error?.response?.data?.message ||
                error?.response?.statusText ||
                error?.message
            )
        }
    }
    return (
        <React.Fragment>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Quiz Name</th>
                            <th>Score</th>
                            <th>Timestamp</th>
                        </tr>
                    </thead>
                    <tbody>
                      {submissions.length > 0 ?
                    (submissions.map((submission) => {
                        <tr key = {submission.submissionId}>
                            <td>{submission.quizId}</td>
                            <td>{submission.score}</td>
                            <td>{submission.timestamp}</td>
                        </tr>
                    }))
                    : (<tr>
                        <td>No submissions</td>
                        </tr>)}

                    </tbody>
                </table>
                
            </div>
        </React.Fragment>
    )
}

export default SubmissionReports