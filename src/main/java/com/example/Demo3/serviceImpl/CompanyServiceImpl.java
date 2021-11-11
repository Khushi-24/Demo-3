package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.CompanyDto;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.Company;
import com.example.Demo3.entities.User;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.CompanyRepository;
import com.example.Demo3.repository.UserRepository;
import com.example.Demo3.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    private final AreaRepository areaRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    @Override
    public CompanyDto addCompany(CompanyDto companyDto) {
        Area area = areaRepository.findById(companyDto.getAreaId()).orElseThrow(()->
                new NotFoundException(HttpStatus.NOT_FOUND,"Area doesn't exist with areaId " + companyDto.getAreaId()));
        if(!userRepository.existsByUserEmail(companyDto.getUser().getUserEmail())){
            User user = modelMapper.map(companyDto.getUser(), User.class);
            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            userRepository.save(user);
            companyDto.setAdminName(user.getUserName());
            companyDto.setAdminEmail(user.getUserEmail());
            Company company = modelMapper.map(companyDto, Company.class);
            companyRepository.save(company);
            companyDto.setUser(null);
            return companyDto;
        }
        else {
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "Company Admin Already Exists.");
        }
    }

    @Override
    public Page<CompanyDto> getAllCompanies(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Company> companies = companyRepository.findAll(pageable);
        List<CompanyDto> companyDtoList = companies.stream().map((Company company) ->
                new CompanyDto(
                        company.getCompanyId(),
                        company.getCompanyName())).collect(Collectors.toList());
        return new PageImpl<>(companyDtoList,  pageable, companies.getTotalElements());
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
