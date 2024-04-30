package com.zigtong.clientserver.domain.resume.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zigtong.clientserver.domain.certificate.entity.Certificate;
import com.zigtong.clientserver.domain.certificate.repository.CertificateRepository;
import com.zigtong.clientserver.domain.resume.dto.request.CertificateUpdateRequest;
import com.zigtong.clientserver.domain.resume.entity.Resume;
import com.zigtong.clientserver.domain.resume.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
	private final ResumeRepository resumeRepository;

	private final CertificateRepository certificateRepository;

	public void updateCertificates(CertificateUpdateRequest request, String workerId) {
		Resume resume = resumeRepository.findById(workerId)
			.orElseThrow();
		List<Certificate> certificates = certificateRepository.findAllById(request.ids());

		resume.updateCertificates(certificates);

		resumeRepository.save(resume);
	}
}
