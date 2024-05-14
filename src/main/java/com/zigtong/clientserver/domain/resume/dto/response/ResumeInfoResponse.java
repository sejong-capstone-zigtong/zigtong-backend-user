package com.zigtong.clientserver.domain.resume.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.zigtong.clientserver.domain.certificate.entity.Certificate;
import com.zigtong.clientserver.domain.resume.entity.Career;
import com.zigtong.clientserver.domain.resume.entity.Resume;
import com.zigtong.clientserver.domain.worker.entity.Worker;

import lombok.Builder;
import lombok.Getter;

@Builder
public record ResumeInfoResponse(
	String name,
	String phoneNumber,
	String profileImageUrl,
	String content,
	List<CareerInfoResponse> careers,
	List<CertificateInfoResponse> certificates
) {
	public static ResumeInfoResponse of(Resume resume) {
		Worker worker = resume.getWorker();
		List<Career> careers = resume.getCareers();
		List<Certificate> certificates = resume.getResumeCertificateRelations().stream()
			.map(relation -> relation.getCertificate())
			.collect(Collectors.toUnmodifiableList());

		return ResumeInfoResponse.builder()
			.name(worker.getName())
			.phoneNumber(worker.getPhoneNumber())
			.profileImageUrl(resume.getUploadedUrl())
			.content(resume.getStatement())
			.careers(careers.stream()
				.map(CareerInfoResponse::new)
				.collect(Collectors.toUnmodifiableList()))
			.certificates(certificates.stream()
				.map(CertificateInfoResponse::new)
				.collect(Collectors.toUnmodifiableList()))
			.build();
	}

	@Getter
	static class CareerInfoResponse {
		private final String companyName;
		private final String role;
		private final String roleDetail;
		private final LocalDate startDate;
		private final LocalDate endDate;

		public CareerInfoResponse(Career career) {
			this.companyName = career.getCompanyName();
			this.role = career.getRole();
			this.roleDetail = career.getRoleDetail();
			this.startDate = career.getStartDate();
			this.endDate = career.getEndDate();
		}
	}

	@Getter
	static class CertificateInfoResponse {
		private final String name;

		public CertificateInfoResponse(Certificate certificate) {
			this.name = certificate.getItem();
		}
	}
}
