package com.credit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.breach.dto.BreachEngineInput;
import com.breach.dto.DataDragAndDrop;
import com.breach.dto.ResponseDto;
import com.breach.dto.RiskChecking;
import com.breach.entity.Breach;
import com.breach.entity.BussinessArea;
import com.breach.entity.Categories;
import com.breach.entity.Franchise;
import com.breach.entity.IdentifiedBeach;
import com.breach.entity.RiskCalculation;
import com.breach.repository.BreachRepository;
import com.breach.repository.BussinessAreaRepository;
import com.breach.repository.CategoriesRepository;
import com.breach.repository.FranchiseRepository;
import com.breach.repository.IdentifiedBeachRepository;
import com.breach.repository.RiskCalculationRepository;
import com.breach.repository.UserDetailsRepository;
import com.breach.service.BranchServiceImpl;

@RunWith(SpringRunner.class)
public class BranchServiceImplTest {

	@InjectMocks
	BranchServiceImpl branchServiceImpl;

	@Mock
	UserDetailsRepository userDetailsRepository;
	@Mock
	BreachRepository breachRepository;
	@Mock
	RiskCalculationRepository riskCalculationRepository;

	@Mock
	FranchiseRepository franchiseRepository;
	@Mock
	BussinessAreaRepository bussinessAreaRepository;
	@Mock
	CategoriesRepository categoriesRepository;
	@Mock
	IdentifiedBeachRepository identifiedBeachRepository;

	Breach breach;
	List<Breach> breachList;
	RiskCalculation riskCalculation;
	List<RiskCalculation> riskCalculationList;

	BreachEngineInput breachEngineInput;

	Franchise franchise;
	List<Franchise> franchiseList;
	BussinessArea bussinessArea;
	List<BussinessArea> bussinessArealist;

	Categories categories;
	List<Categories> categorieslist;

	IdentifiedBeach identifiedBeach;
	List<IdentifiedBeach> identifiedBeachList;

	@Before
	public void setup() {
		breach = new Breach();
		breachEngineInput = new BreachEngineInput();
		riskCalculation = new RiskCalculation();
		breachList = new ArrayList<>();

		riskCalculationList = new ArrayList<>();

		franchise = new Franchise();
		franchiseList = new ArrayList<>();
		franchiseList.add(franchise);
		bussinessArea = new BussinessArea();
		bussinessArealist = new ArrayList<>();
		bussinessArealist.add(bussinessArea);
		categories = new Categories();
		categorieslist = new ArrayList<>();
		categorieslist.add(categories);
		identifiedBeach = new IdentifiedBeach();
		identifiedBeachList = new ArrayList<>();
		identifiedBeachList.add(identifiedBeach);
	}

	@Test
	public void breachEngine() {

//		Mockito.when(riskCalculationRepository.findByFranchiseIdAndBusinessAreaIdAndCategoriseId(Mockito.anyInt(), Mockito.any(), Mockito.any())).thenReturn(riskCalculationList);
//		Mockito.when(breachRepository.save(breach)).thenReturn(breach);
		ResponseEntity<ResponseDto> actual = branchServiceImpl.breachEngine(breachEngineInput);

		Assert.assertEquals(HttpStatus.CREATED.value(), actual.getStatusCodeValue());

	}

	@Test
	public void breachEngineNegative() {
		breachEngineInput.setCategoryId(3);
		riskCalculationList = new ArrayList<>();
		breach.setBreachId(1);
		breach.setBusinessId(1);
		breach.setCategoryId(1);
		breach.setCreatedDate(LocalDateTime.now());
		breach.setFranchiseId(1);
		breach.setRisk("HIGH");
		breach.setStatus("open");
		breach.setUserId(1);
		breachList.add(breach);

		breachEngineInput.setBussinessId(breach.getBusinessId());
		breachEngineInput.setCategoryId(breach.getCategoryId());
		breachEngineInput.setFranchiseId(breach.getFranchiseId());

		Mockito.when(riskCalculationRepository.findByFranchiseIdAndBusinessAreaIdAndCategoriseId(
				breachEngineInput.getFranchiseId(), breachEngineInput.getBussinessId(),
				breachEngineInput.getCategoryId())).thenReturn(riskCalculationList);
//		Mockito.when(breachRepository.save(breach)).thenReturn(breach);
		ResponseEntity<ResponseDto> actual = branchServiceImpl.breachEngine(breachEngineInput);
		Assert.assertEquals(HttpStatus.CREATED.value(), actual.getStatusCodeValue());

	}

	@Test
	public void riskCheck() {

//		Mockito.when(riskCalculationRepository.findByFranchiseIdAndBusinessAreaIdAndCategoriseId(Mockito.anyInt(), Mockito.any(), Mockito.any())).thenReturn(riskCalculationList);
		ResponseEntity<RiskChecking> actual = branchServiceImpl.riskCheck(breachEngineInput);

		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());

	}

	@Test
	public void franchise() {
		Mockito.when(franchiseRepository.findAll()).thenReturn(franchiseList);

		ResponseEntity<List<DataDragAndDrop>> actual = branchServiceImpl.franchise();
		Assert.assertEquals(200, actual.getStatusCodeValue());

	}

	@Test
	public void bussiness() {
		Mockito.when(bussinessAreaRepository.findByFranchiseId(Mockito.any())).thenReturn(bussinessArealist);

		ResponseEntity<List<DataDragAndDrop>> actual = branchServiceImpl.bussiness(1);
		Assert.assertEquals(200, actual.getStatusCodeValue());

	}

	@Test
	public void category() {
		Mockito.when(categoriesRepository.findAll()).thenReturn(categorieslist);

		ResponseEntity<List<DataDragAndDrop>> actual = branchServiceImpl.category();
		Assert.assertEquals(200, actual.getStatusCodeValue());

	}

	@Test
	public void identifiedBreach() {
		Mockito.when(identifiedBeachRepository.findAll()).thenReturn(identifiedBeachList);
		ResponseEntity<List<DataDragAndDrop>> actual = branchServiceImpl.identifiedBreach();
		Assert.assertEquals(200, actual.getStatusCodeValue());

	}

}
