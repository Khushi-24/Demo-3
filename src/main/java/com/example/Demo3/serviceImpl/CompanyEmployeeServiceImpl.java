package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.CompanyEmployeeDto;
import com.example.Demo3.entities.*;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.*;
import com.example.Demo3.service.CompanyEmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyEmployeeServiceImpl implements CompanyEmployeeService {

    private final CompanyEmployeeRepository companyEmployeeRepository;

    private final MemberRepository memberRepository;

    private final AreaRepository areaRepository;

    private final SocietyRepository societyRepository;

    private final CompanyRepository companyRepository;

    private final CityRepository cityRepository;

    private final FamilyRepository familyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto) {

        if(companyEmployeeRepository.existsByMembersMemberIdAndCompanyCompanyId(
                companyEmployeeDto.getMemberDto().getMemberId(),
                companyEmployeeDto.getCompanyDto().getCompanyId())){
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "Employee Already exists in company");
        }else{
            Members members = memberRepository.findById(companyEmployeeDto.getMemberDto().getMemberId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    "Member doesn't exists."));
            if(members.getIsWorking()){
                if(companyRepository.existsByCompanyIdAndCityCityId(companyEmployeeDto.getCompanyDto().getCompanyId(), companyEmployeeDto.getCityDto().getCityId())){
//                    Family family = familyRepository.getById(members.getFamily().getFamilyId());
//                    Society society = societyRepository.getById(family.getSociety().getSocietyId());
//                    Area area = areaRepository.getById(society.getArea().getAreaId());
//                    City city = cityRepository.getById(area.getCity().getCityId());
//                    if(city.getCityId() == companyEmployeeDto.getCityDto().getCityId()){
//                        CompanyEmployee companyEmployee = new CompanyEmployee();
//                        modelMapper.map(companyEmployeeDto, companyEmployee);
//                        return companyEmployeeDto;
//                    }else{
//                        new BadRequestException(HttpStatus.BAD_REQUEST, "Member Belongs to different city.");
//                    }
                    if(companyEmployeeRepository.existsByMembersMemberId(companyEmployeeDto.getMemberDto().getMemberId())){
                        CompanyEmployee companyEmployee = companyEmployeeRepository.findByMembersMemberId(companyEmployeeDto.getMemberDto().getMemberId());
                        Long salary = companyEmployee.getSalary();
                        companyEmployee.setAggregatedSalary(salary + companyEmployeeDto.getSalary());
                        companyEmployeeDto.setAggregatedSalary(companyEmployee.getAggregatedSalary());
                    }else{
                        companyEmployeeDto.setAggregatedSalary(companyEmployeeDto.getSalary());
                    }
                    companyEmployeeDto.setEmployeeName(members.getMemberName());
                    CompanyEmployee companyEmployee = new CompanyEmployee();
                    modelMapper.map(companyEmployeeDto, companyEmployee);
                    companyEmployee.setMembers(members);
                    companyEmployeeRepository.save(companyEmployee);
                    return companyEmployeeDto;
                }else {
                    throw new NotFoundException(HttpStatus.NOT_FOUND, "Company doesn't exists in city.");
                }
            }
            else{
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "Member doesn't work.");
            }

        }
    }

}
