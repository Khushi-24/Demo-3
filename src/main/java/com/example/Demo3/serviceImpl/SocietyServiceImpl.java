package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.City;
import com.example.Demo3.entities.Society;
import com.example.Demo3.entities.User;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.repository.SocietyRepository;
import com.example.Demo3.repository.UserRepository;
import com.example.Demo3.service.MailService;
import com.example.Demo3.service.SocietyService;
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
public class SocietyServiceImpl implements SocietyService {

    private final MailService mailService;

    private final AreaRepository areaRepository;

    private final CityRepository cityRepository;

    private final UserRepository userRepository;

    private final SocietyRepository societyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    @Override
    public SocietyDto addSociety(SocietyDto societyDto) {
        Area area = areaRepository.findById(societyDto.getAreaDto().getAreaId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "No such area exists with area Id " + societyDto.getAreaDto().getAreaId()));
        if (!userRepository.existsByUserEmail(societyDto.getUserDto().getUserEmail())) {
            Society society = new Society();
            modelMapper.map(societyDto, society);
            UserDto userDto = societyDto.getUserDto();
            User user = new User();
            modelMapper.map(userDto, user);
            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            userRepository.save(user);
            society.setSocietyAdminEmail(user.getUserEmail());
            societyRepository.save(society);
            User u = userRepository.findByUserRole("Admin");
            MailDto mail = new MailDto();
            mail.setMailFrom("jiyanikhushali24@gmail.com");
            mail.setMailTo(u.getUserEmail());
            mail.setMailSubject("Regarding Admin of Society");
            mail.setMailContent("You have added new Society Admin to " +societyDto.getSocietyName());
            mailService.sendEmail(mail);
            societyDto.getUserDto().setUserPassword(null);
            return societyDto;
        } else {
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "Society Admin Already exists.");
        }
    }

    @Override
    public Page<SocietyDto> getAllSociety(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Society> societies = societyRepository.findAll(pageable);
        List<SocietyDto> societyDtoList = societies.stream().map((Society society) ->
                new SocietyDto(
                        society.getSocietyId(),
                        society.getSocietyName())).collect(Collectors.toList());
        return new PageImpl<>(societyDtoList,  pageable, societyDtoList.size());
    }

    @Override
    public SocietyDto getSocietyBySocietyId(Long societyId) {
        Society society = societyRepository.findById(societyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Society doesn't exists."));
        SocietyDto societyDto = new SocietyDto();
        Area area = areaRepository.findById(society.getArea().getAreaId()).get();
        AreaDto areaDto = new AreaDto();
        City city = cityRepository.findById(area.getCity().getCityId()).get();
        CityDto cityDto = new CityDto();
        modelMapper.map(city, cityDto);
        cityDto.setCityState(null);
        modelMapper.map(area, areaDto);
        areaDto.setCityDto(cityDto);
        modelMapper.map(society,societyDto);
        societyDto.setAreaDto(areaDto);
        return societyDto;
    }

    @Override
    public List<SocietyDto> getSocietyByAreaId(Long areaId) {
        if(areaId != null){
            Area area = areaRepository.findById(areaId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    "Area doesn't exists with areaId " + areaId));
            List<Society> societyList = societyRepository.findAllByAreaAreaId(areaId);
            List<SocietyDto> societyDtoList = societyList.stream().map((Society society) ->
                    new SocietyDto(
                            society.getSocietyId(),
                            society.getSocietyName())).collect(Collectors.toList());
            return societyDtoList;
        }
       else {
           throw new BadRequestException(HttpStatus.BAD_REQUEST, "Area Id can't be null.");
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
