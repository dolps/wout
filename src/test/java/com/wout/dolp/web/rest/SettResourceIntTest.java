package com.wout.dolp.web.rest;

import com.wout.dolp.WoutApp;

import com.wout.dolp.domain.Sett;
import com.wout.dolp.domain.Exercise;
import com.wout.dolp.repository.SettRepository;
import com.wout.dolp.service.SettService;
import com.wout.dolp.service.dto.SettDTO;
import com.wout.dolp.service.mapper.SettMapper;
import com.wout.dolp.web.rest.errors.ExceptionTranslator;
import com.wout.dolp.service.dto.SettCriteria;
import com.wout.dolp.service.SettQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wout.dolp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SettResource REST controller.
 *
 * @see SettResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WoutApp.class)
public class SettResourceIntTest {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Integer DEFAULT_REPS = 1;
    private static final Integer UPDATED_REPS = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private SettRepository settRepository;

    @Autowired
    private SettMapper settMapper;

    @Autowired
    private SettService settService;

    @Autowired
    private SettQueryService settQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSettMockMvc;

    private Sett sett;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SettResource settResource = new SettResource(settService, settQueryService);
        this.restSettMockMvc = MockMvcBuilders.standaloneSetup(settResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sett createEntity(EntityManager em) {
        Sett sett = new Sett()
            .order(DEFAULT_ORDER)
            .reps(DEFAULT_REPS)
            .weight(DEFAULT_WEIGHT);
        return sett;
    }

    @Before
    public void initTest() {
        sett = createEntity(em);
    }

    @Test
    @Transactional
    public void createSett() throws Exception {
        int databaseSizeBeforeCreate = settRepository.findAll().size();

        // Create the Sett
        SettDTO settDTO = settMapper.toDto(sett);
        restSettMockMvc.perform(post("/api/setts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settDTO)))
            .andExpect(status().isCreated());

        // Validate the Sett in the database
        List<Sett> settList = settRepository.findAll();
        assertThat(settList).hasSize(databaseSizeBeforeCreate + 1);
        Sett testSett = settList.get(settList.size() - 1);
        assertThat(testSett.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testSett.getReps()).isEqualTo(DEFAULT_REPS);
        assertThat(testSett.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createSettWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settRepository.findAll().size();

        // Create the Sett with an existing ID
        sett.setId(1L);
        SettDTO settDTO = settMapper.toDto(sett);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettMockMvc.perform(post("/api/setts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sett in the database
        List<Sett> settList = settRepository.findAll();
        assertThat(settList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSetts() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList
        restSettMockMvc.perform(get("/api/setts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sett.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].reps").value(hasItem(DEFAULT_REPS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    @Test
    @Transactional
    public void getSett() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get the sett
        restSettMockMvc.perform(get("/api/setts/{id}", sett.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sett.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.reps").value(DEFAULT_REPS))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getAllSettsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where order equals to DEFAULT_ORDER
        defaultSettShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the settList where order equals to UPDATED_ORDER
        defaultSettShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllSettsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultSettShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the settList where order equals to UPDATED_ORDER
        defaultSettShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllSettsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where order is not null
        defaultSettShouldBeFound("order.specified=true");

        // Get all the settList where order is null
        defaultSettShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllSettsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where order greater than or equals to DEFAULT_ORDER
        defaultSettShouldBeFound("order.greaterOrEqualThan=" + DEFAULT_ORDER);

        // Get all the settList where order greater than or equals to UPDATED_ORDER
        defaultSettShouldNotBeFound("order.greaterOrEqualThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllSettsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where order less than or equals to DEFAULT_ORDER
        defaultSettShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the settList where order less than or equals to UPDATED_ORDER
        defaultSettShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }


    @Test
    @Transactional
    public void getAllSettsByRepsIsEqualToSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where reps equals to DEFAULT_REPS
        defaultSettShouldBeFound("reps.equals=" + DEFAULT_REPS);

        // Get all the settList where reps equals to UPDATED_REPS
        defaultSettShouldNotBeFound("reps.equals=" + UPDATED_REPS);
    }

    @Test
    @Transactional
    public void getAllSettsByRepsIsInShouldWork() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where reps in DEFAULT_REPS or UPDATED_REPS
        defaultSettShouldBeFound("reps.in=" + DEFAULT_REPS + "," + UPDATED_REPS);

        // Get all the settList where reps equals to UPDATED_REPS
        defaultSettShouldNotBeFound("reps.in=" + UPDATED_REPS);
    }

    @Test
    @Transactional
    public void getAllSettsByRepsIsNullOrNotNull() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where reps is not null
        defaultSettShouldBeFound("reps.specified=true");

        // Get all the settList where reps is null
        defaultSettShouldNotBeFound("reps.specified=false");
    }

    @Test
    @Transactional
    public void getAllSettsByRepsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where reps greater than or equals to DEFAULT_REPS
        defaultSettShouldBeFound("reps.greaterOrEqualThan=" + DEFAULT_REPS);

        // Get all the settList where reps greater than or equals to UPDATED_REPS
        defaultSettShouldNotBeFound("reps.greaterOrEqualThan=" + UPDATED_REPS);
    }

    @Test
    @Transactional
    public void getAllSettsByRepsIsLessThanSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where reps less than or equals to DEFAULT_REPS
        defaultSettShouldNotBeFound("reps.lessThan=" + DEFAULT_REPS);

        // Get all the settList where reps less than or equals to UPDATED_REPS
        defaultSettShouldBeFound("reps.lessThan=" + UPDATED_REPS);
    }


    @Test
    @Transactional
    public void getAllSettsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where weight equals to DEFAULT_WEIGHT
        defaultSettShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the settList where weight equals to UPDATED_WEIGHT
        defaultSettShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllSettsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultSettShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the settList where weight equals to UPDATED_WEIGHT
        defaultSettShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllSettsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where weight is not null
        defaultSettShouldBeFound("weight.specified=true");

        // Get all the settList where weight is null
        defaultSettShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllSettsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where weight greater than or equals to DEFAULT_WEIGHT
        defaultSettShouldBeFound("weight.greaterOrEqualThan=" + DEFAULT_WEIGHT);

        // Get all the settList where weight greater than or equals to UPDATED_WEIGHT
        defaultSettShouldNotBeFound("weight.greaterOrEqualThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllSettsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);

        // Get all the settList where weight less than or equals to DEFAULT_WEIGHT
        defaultSettShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the settList where weight less than or equals to UPDATED_WEIGHT
        defaultSettShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllSettsByExerciseIsEqualToSomething() throws Exception {
        // Initialize the database
        Exercise exercise = ExerciseResourceIntTest.createEntity(em);
        em.persist(exercise);
        em.flush();
        sett.setExercise(exercise);
        settRepository.saveAndFlush(sett);
        Long exerciseId = exercise.getId();

        // Get all the settList where exercise equals to exerciseId
        defaultSettShouldBeFound("exerciseId.equals=" + exerciseId);

        // Get all the settList where exercise equals to exerciseId + 1
        defaultSettShouldNotBeFound("exerciseId.equals=" + (exerciseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSettShouldBeFound(String filter) throws Exception {
        restSettMockMvc.perform(get("/api/setts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sett.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].reps").value(hasItem(DEFAULT_REPS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSettShouldNotBeFound(String filter) throws Exception {
        restSettMockMvc.perform(get("/api/setts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSett() throws Exception {
        // Get the sett
        restSettMockMvc.perform(get("/api/setts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSett() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);
        int databaseSizeBeforeUpdate = settRepository.findAll().size();

        // Update the sett
        Sett updatedSett = settRepository.findOne(sett.getId());
        // Disconnect from session so that the updates on updatedSett are not directly saved in db
        em.detach(updatedSett);
        updatedSett
            .order(UPDATED_ORDER)
            .reps(UPDATED_REPS)
            .weight(UPDATED_WEIGHT);
        SettDTO settDTO = settMapper.toDto(updatedSett);

        restSettMockMvc.perform(put("/api/setts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settDTO)))
            .andExpect(status().isOk());

        // Validate the Sett in the database
        List<Sett> settList = settRepository.findAll();
        assertThat(settList).hasSize(databaseSizeBeforeUpdate);
        Sett testSett = settList.get(settList.size() - 1);
        assertThat(testSett.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testSett.getReps()).isEqualTo(UPDATED_REPS);
        assertThat(testSett.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingSett() throws Exception {
        int databaseSizeBeforeUpdate = settRepository.findAll().size();

        // Create the Sett
        SettDTO settDTO = settMapper.toDto(sett);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSettMockMvc.perform(put("/api/setts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settDTO)))
            .andExpect(status().isCreated());

        // Validate the Sett in the database
        List<Sett> settList = settRepository.findAll();
        assertThat(settList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSett() throws Exception {
        // Initialize the database
        settRepository.saveAndFlush(sett);
        int databaseSizeBeforeDelete = settRepository.findAll().size();

        // Get the sett
        restSettMockMvc.perform(delete("/api/setts/{id}", sett.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sett> settList = settRepository.findAll();
        assertThat(settList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sett.class);
        Sett sett1 = new Sett();
        sett1.setId(1L);
        Sett sett2 = new Sett();
        sett2.setId(sett1.getId());
        assertThat(sett1).isEqualTo(sett2);
        sett2.setId(2L);
        assertThat(sett1).isNotEqualTo(sett2);
        sett1.setId(null);
        assertThat(sett1).isNotEqualTo(sett2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettDTO.class);
        SettDTO settDTO1 = new SettDTO();
        settDTO1.setId(1L);
        SettDTO settDTO2 = new SettDTO();
        assertThat(settDTO1).isNotEqualTo(settDTO2);
        settDTO2.setId(settDTO1.getId());
        assertThat(settDTO1).isEqualTo(settDTO2);
        settDTO2.setId(2L);
        assertThat(settDTO1).isNotEqualTo(settDTO2);
        settDTO1.setId(null);
        assertThat(settDTO1).isNotEqualTo(settDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(settMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(settMapper.fromId(null)).isNull();
    }
}
