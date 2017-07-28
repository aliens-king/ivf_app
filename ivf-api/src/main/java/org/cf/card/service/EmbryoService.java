/**
 *
 */
package org.cf.card.service;

import java.util.Collection;
import java.util.List;

import org.cf.card.dto.CycleDto;
import org.cf.card.dto.EmbryoCodeDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.EmbryoDto;
import org.cf.card.dto.UIDayDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.Embryo;
import org.cf.card.model.EmbryoCode;

/**
 * The Interface EmbryoService.
 */
public interface EmbryoService extends BaseService<Embryo> {

	/**
	 * Find embryo by treatment id.
	 *
	 * @param codeId
	 *            the treatment id
	 * @return the list
	 */
	List<Embryo> findEmbryoByCodeId(Long codeId);

	/**
	 * Find embryo treatment by treatment id.
	 *
	 * @param codeId
	 *            the treatment id
	 * @param moduleId
	 *            : the id of screen fo whih values have to be fetched
	 * @return the list
	 */
	List<EmbryoCode> findByCodeId(Long codeId);

	/**
	 * Find embryo treatment by embryo treatment id.
	 *
	 * @param embryoCodeId
	 *            the embryo treatment id
	 * @return the embryo treatment
	 */
	EmbryoCode findEmbryoCodeByEmbryoCodeId(Long embryoCodeId);

	/**
	 * Create new embryos as per no of oocytes
	 *
	 * @param oocytes
	 *            the oocytes
	 * @param codeId
	 *            the code id
	 * @return the list
	 */
	List<EmbryoCode> saveAll(int oocytes, long codeId);

	/**
	 * Create new embryos as per no of oocytes
	 *
	 * @param oocytes
	 *            the oocytes
	 * @param codeId
	 *            the code id
	 * @return
	 */
	List<Embryo> saveEmbryos(int oocytes, long codeId);

	void saveCryoEmbryoEgg(EmbryoCodeDto embryoCodeDto);

	List<DayProgressValue> saveDayProgressValue(UIDayDto uiDayDto);

	List<DayProgressValue> findByEmbryoCodeIdAndDayIndex(Long embryoCodeid, int datIndex);

	EmbryoCode addEmbryoTransfer(EmbryoCodeValueDto embryoCodeValueDto);

	List<EmbryoDto> updateEmbryos(List<EmbryoDto> aoEmbryoDto);

	/**
	 * Update embryo code value for remarks only.
	 *
	 * @param embryoCodeValueDto the embryo code value dto
	 */
	public void updateEmbryoCodeValueDto(EmbryoCodeValueDto embryoCodeValueDto);


	/**
	 * @param clientId
	 * @return
	 */
	List<EmbryoCode> findEmbryoCodeByClientId(Long clientId);

	/*Set<CycleDto> findEmbryoCyclesByWoman(Long womanClientId);

	Set<CycleDto> findSemenCyclesByMan(Long manClientId);*/

	Collection<CycleDto> findCoupleCycles(Long womanClientId, Long partnerClientId);

	List<Long> findCodeIdByCoupleIdAndStartDate(Long coupleId, String startDate);

	/**
	 * Find day progress value by embryo code id and destiny.
	 *
	 * @param embryoCodeId the embryo code id
	 * @param destiny the destiny
	 * @return the list
	 */
	List<DayProgressValue> findDayProgressValueByEmbryoCodeIdAndDestiny(List<Long> embryoCodeId, int destiny);
}
