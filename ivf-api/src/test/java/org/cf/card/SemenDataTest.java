package org.cf.card;

import java.util.Collections;
import java.util.List;

import org.cf.card.model.SemenCode;
import org.cf.card.service.SemenService;
import org.cf.card.vo.VoSemenCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SemenDataTest extends BaseTest {

	@Autowired
	private SemenService semenService;

	@Test
	public void test() {
		List<VoSemenCode> aoVoSemenCode = semenService.findSemenCodeAndSemenDataByClientId(19l);
		Collections.reverse(aoVoSemenCode);
		try {
			System.out.println(aoVoSemenCode);
			String code = "";
			boolean flag =false;
			for (VoSemenCode voSemenCode : aoVoSemenCode) {
				flag = true;
				if (code.equals(voSemenCode.getCode())) {
					System.out.println("    " + " " + voSemenCode.getDateUsed() + " " + voSemenCode.getIndex());
					flag = false;
				} else {
					System.out.println(voSemenCode.getCode() + " " + voSemenCode.getDateUsed() + " " + voSemenCode.getIndex());
					code = voSemenCode.getCode();
					flag = true;
				}
				if(flag)
					System.out.println("------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<SemenCode> aoSemenCode = semenService.findSemenCodeByClientId(19l);
		System.out.println(aoSemenCode);
	}

}
