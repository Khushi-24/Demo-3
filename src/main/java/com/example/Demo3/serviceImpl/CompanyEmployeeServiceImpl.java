package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.Company;
import com.example.Demo3.entities.CompanyEmployee;
import com.example.Demo3.entities.Members;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.*;
import com.example.Demo3.service.CompanyEmployeeService;
import com.example.Demo3.service.MailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private final MailService mailService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto) {

        Members members = memberRepository.findById(companyEmployeeDto.getMemberDto().getMemberId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Member doesn't exists."));
        Company company = companyRepository.findById(companyEmployeeDto.getCompanyDto().getCompanyId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Company doesn't exists."));
        if(companyEmployeeRepository.existsByMembersMemberIdAndCompanyCompanyId(
                companyEmployeeDto.getMemberDto().getMemberId(),
                companyEmployeeDto.getCompanyDto().getCompanyId())){
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "Employee Already exists in company");
        }else{
            if(members.getIsWorking()){
                    if(companyEmployeeRepository.existsByMembersMemberId(companyEmployeeDto.getMemberDto().getMemberId())){
                        if(companyEmployeeRepository.countByMembersMemberId(companyEmployeeDto.getMemberDto().getMemberId()) < 2){
                            CompanyEmployee companyEmployee = companyEmployeeRepository.findByMembersMemberId(companyEmployeeDto.getMemberDto().getMemberId());
                            Company company1 = companyRepository.findById(companyEmployee.getCompany().getCompanyId()).get();
                            Area area1 = areaRepository.getById(company1.getArea().getAreaId());
                            Area area2 = areaRepository.getById(company.getArea().getAreaId());
                            if(area1.getCity().getCityId() == area2.getCity().getCityId()){
                                Long salary = companyEmployee.getSalary();
                                companyEmployee.setAggregatedSalary(salary + companyEmployeeDto.getSalary());
                                companyEmployeeDto.setAggregatedSalary(companyEmployee.getAggregatedSalary());
                            }else{
                                throw new BadRequestException(HttpStatus.BAD_REQUEST, "Employee can't work in two different cities at a time.");
                            }

                        }else{
                            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Employee is already working in two companies.");
                        }

                    }else{
                        companyEmployeeDto.setAggregatedSalary(companyEmployeeDto.getSalary());
                    }
                    CompanyEmployee companyEmployee = new CompanyEmployee();
                    modelMapper.map(companyEmployeeDto, companyEmployee);
                    companyEmployee.setMembers(members);
                    companyEmployee.setCreatedTimeStamp(new Date());
                    MailDto mail = new MailDto();
                    mail.setMailFrom("jiyanikhushali24@gmail.com");
                    mail.setMailTo(company.getAdminEmail());
                    mail.setMailSubject("Regarding Employees of Company");
                    mail.setMailContent("A new employee has been added to your company");
                    mailService.sendEmail(mail);
                    companyEmployeeRepository.save(companyEmployee);
                    return companyEmployeeDto;
            }else{
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "Member doesn't work.");
            }
        }

    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessByAreaId(RequestDtoForGettingEmployeesByAreaId dto) {

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryLessThanAndByAreaId(
                dto.getAreaId(),
                dto.getSalary()
        );


        List<CompanyEmployeeDto> companyEmployeeDtoList = companyEmployees.stream().map((CompanyEmployee employee)->

                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getDesignation(),
                        employee.getSalary())).collect(Collectors.toList());
        return companyEmployeeDtoList;
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessThanAndByCityId(RequestDtoForGettingEmployeesByCityId dto) {

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryLessThanAndByCityId(
                dto.getCityId(),
                dto.getSalary()
        );
        List<CompanyEmployeeDto> companyEmployeeDtoList = companyEmployees.stream().map((CompanyEmployee employee)->
                new CompanyEmployeeDto(employee.getCompanyEmployeeId() ,
                        employee.getDesignation(),
                        employee.getSalary())).collect(Collectors.toList());
        return companyEmployeeDtoList;
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessThanAndBySocietyId(RequestDtoForGettingEmployeesBySocietyId dto) {
//        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryLessThanAndBySocietyId(
//                dto.getSocietyId(),
//                dto.getSalary()
//        );
//        List<CompanyEmployeeDto> companyEmployeeDtoList = companyEmployees.stream().map((CompanyEmployee employee)->
//                new CompanyEmployeeDto(employee.getCompanyEmployeeId() ,
//                        employee.getEmployeeName(),
//                        employee.getSalary())).collect(Collectors.toList());
//        return companyEmployeeDtoList;
        return null;
    }

    @Override
    public Page<CompanyEmployeeDto> getEmployeeListByKeywordAndCompanyId(RequestDtoToGetEmployeeListByKeywordAndCompanyId dto) {
//        int pageSize = 5;
//        int pageNo = dto.getPageNo();
//        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
//        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.findEmployeeByKeyword(dto.getCompanyId(),dto.getKeyword());
//        List<CompanyEmployeeDto> companyEmployeeList = companyEmployees.stream().map((CompanyEmployee c) ->
//                new CompanyEmployeeDto(
//                        c.getCompanyEmployeeId(),
//                        c.getEmployeeName())).collect(Collectors.toList());
//        return new PageImpl<>(companyEmployeeList,  pageable, companyEmployeeList.size());
        return null;
    }

}
