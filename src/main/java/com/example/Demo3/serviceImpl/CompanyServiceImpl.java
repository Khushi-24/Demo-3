package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.CompanyDto;
import com.example.Demo3.entities.City;
import com.example.Demo3.entities.Company;
import com.example.Demo3.entities.User;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.repository.CompanyRepository;
import com.example.Demo3.repository.UserRepository;
import com.example.Demo3.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    @Override
    public CompanyDto addCompany(CompanyDto companyDto) {
        City city = cityRepository.findById(companyDto.getCityDto().getCityId()).orElseThrow(()->
                new NotFoundException(HttpStatus.NOT_FOUND,"City doesn't exist with cityId " + companyDto.getCityDto().getCityId()));
        if(!userRepository.existsByUserEmail(companyDto.getUser().getUserEmail())){
            User user = new User();
            modelMapper.map(companyDto.getUser(), user);
            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            userRepository.save(user);
            Company company = new Company();
            companyDto.setAdminName(user.getUserName());
            companyDto.setAdminEmail(user.getUserEmail());
            modelMapper.map(companyDto, company);
            companyRepository.save(company);
            companyDto.setUser(null);
            return companyDto;
        }
        else {
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "Company Admin Already Exists.");
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
