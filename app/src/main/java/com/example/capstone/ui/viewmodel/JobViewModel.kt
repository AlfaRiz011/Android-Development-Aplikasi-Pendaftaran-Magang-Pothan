import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.data.model.JobApply
import com.example.capstone.data.model.Lowongan
import com.example.capstone.data.model.PesertaAktif
import com.example.capstone.data.repository.JobRepository
import com.example.capstone.data.request.JobBodyRequest
import com.example.capstone.data.response.GenericResponse

class JobViewModel(private val jobRepository: JobRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = jobRepository.isLoading

    fun getAllJobs(): LiveData<GenericResponse<List<Lowongan>>> {
        return jobRepository.getAllJobs()
    }

    fun getRegisteredJobs(userId: String): LiveData<GenericResponse<List<JobApply>>> {
        return jobRepository.getRegisteredJobs(userId)
    }

    fun getAllJobsAdmin(): LiveData<GenericResponse<List<PesertaAktif>>> {
        return jobRepository.getAllJobsAdmin()
    }

    fun getVerifiedRegistration(regisId: String): LiveData<GenericResponse<PesertaAktif>> {
        return jobRepository.getVerifiedRegistration(regisId)
    }

    fun createJob(jobRequest: JobBodyRequest): LiveData<GenericResponse<Lowongan>> {
        return jobRepository.createJob(jobRequest)
    }

    fun verifyJobRegistration(regisId: String): LiveData<GenericResponse<JobApply>> {
        return jobRepository.verifyJobRegistration(regisId)
    }

    fun rejectJobRegistration(regisId: String): LiveData<GenericResponse<JobApply>> {
        return jobRepository.rejectJobRegistration(regisId)
    }
}
