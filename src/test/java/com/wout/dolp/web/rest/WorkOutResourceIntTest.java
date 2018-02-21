package com.wout.dolp.web.rest;

import com.wout.dolp.WoutApp;

import com.wout.dolp.domain.WorkOut;
import com.wout.dolp.repository.WorkOutRepository;
import com.wout.dolp.service.WorkOutService;
import com.wout.dolp.service.dto.WorkOutDTO;
import com.wout.dolp.service.mapper.WorkOutMapper;
import com.wout.dolp.web.rest.errors.ExceptionTranslator;

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
 * Test class for the WorkOutResource REST controller.
 *
 * @see WorkOutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WoutApp.class)
public class WorkOutResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private WorkOutRepository workOutRepository;

    @Autowired
    private WorkOutMapper workOutMapper;

    @Autowired
    private WorkOutService workOutService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkOutMockMvc;

    private WorkOut workOut;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkOutResource workOutResource = new WorkOutResource(workOutService);
        this.restWorkOutMockMvc = MockMvcBuilders.standaloneSetup(workOutResource)
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
    public static WorkOut createEntity(EntityManager em) {
        WorkOut workOut = new WorkOut()
            .name(DEFAULT_NAME);
        return workOut;
    }

    @Before
    public void initTest() {
        workOut = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkOut() throws Exception {
        int databaseSizeBeforeCreate = workOutRepository.findAll().size();

        // Create the WorkOut
        WorkOutDTO workOutDTO = workOutMapper.toDto(workOut);
        restWorkOutMockMvc.perform(post("/api/work-outs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOutDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOut in the database
        List<WorkOut> workOutList = workOutRepository.findAll();
        assertThat(workOutList).hasSize(databaseSizeBeforeCreate + 1);
        WorkOut testWorkOut = workOutList.get(workOutList.size() - 1);
        assertThat(testWorkOut.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWorkOutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workOutRepository.findAll().size();

        // Create the WorkOut with an existing ID
        workOut.setId(1L);
        WorkOutDTO workOutDTO = workOutMapper.toDto(workOut);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkOutMockMvc.perform(post("/api/work-outs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkOut in the database
        List<WorkOut> workOutList = workOutRepository.findAll();
        assertThat(workOutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkOuts() throws Exception {
        // Initialize the database
        workOutRepository.saveAndFlush(workOut);

        // Get all the workOutList
        restWorkOutMockMvc.perform(get("/api/work-outs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWorkOut() throws Exception {
        // Initialize the database
        workOutRepository.saveAndFlush(workOut);

        // Get the workOut
        restWorkOutMockMvc.perform(get("/api/work-outs/{id}", workOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workOut.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkOut() throws Exception {
        // Get the workOut
        restWorkOutMockMvc.perform(get("/api/work-outs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkOut() throws Exception {
        // Initialize the database
        workOutRepository.saveAndFlush(workOut);
        int databaseSizeBeforeUpdate = workOutRepository.findAll().size();

        // Update the workOut
        WorkOut updatedWorkOut = workOutRepository.findOne(workOut.getId());
        // Disconnect from session so that the updates on updatedWorkOut are not directly saved in db
        em.detach(updatedWorkOut);
        updatedWorkOut
            .name(UPDATED_NAME);
        WorkOutDTO workOutDTO = workOutMapper.toDto(updatedWorkOut);

        restWorkOutMockMvc.perform(put("/api/work-outs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOutDTO)))
            .andExpect(status().isOk());

        // Validate the WorkOut in the database
        List<WorkOut> workOutList = workOutRepository.findAll();
        assertThat(workOutList).hasSize(databaseSizeBeforeUpdate);
        WorkOut testWorkOut = workOutList.get(workOutList.size() - 1);
        assertThat(testWorkOut.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkOut() throws Exception {
        int databaseSizeBeforeUpdate = workOutRepository.findAll().size();

        // Create the WorkOut
        WorkOutDTO workOutDTO = workOutMapper.toDto(workOut);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkOutMockMvc.perform(put("/api/work-outs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOutDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOut in the database
        List<WorkOut> workOutList = workOutRepository.findAll();
        assertThat(workOutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkOut() throws Exception {
        // Initialize the database
        workOutRepository.saveAndFlush(workOut);
        int databaseSizeBeforeDelete = workOutRepository.findAll().size();

        // Get the workOut
        restWorkOutMockMvc.perform(delete("/api/work-outs/{id}", workOut.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkOut> workOutList = workOutRepository.findAll();
        assertThat(workOutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOut.class);
        WorkOut workOut1 = new WorkOut();
        workOut1.setId(1L);
        WorkOut workOut2 = new WorkOut();
        workOut2.setId(workOut1.getId());
        assertThat(workOut1).isEqualTo(workOut2);
        workOut2.setId(2L);
        assertThat(workOut1).isNotEqualTo(workOut2);
        workOut1.setId(null);
        assertThat(workOut1).isNotEqualTo(workOut2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOutDTO.class);
        WorkOutDTO workOutDTO1 = new WorkOutDTO();
        workOutDTO1.setId(1L);
        WorkOutDTO workOutDTO2 = new WorkOutDTO();
        assertThat(workOutDTO1).isNotEqualTo(workOutDTO2);
        workOutDTO2.setId(workOutDTO1.getId());
        assertThat(workOutDTO1).isEqualTo(workOutDTO2);
        workOutDTO2.setId(2L);
        assertThat(workOutDTO1).isNotEqualTo(workOutDTO2);
        workOutDTO1.setId(null);
        assertThat(workOutDTO1).isNotEqualTo(workOutDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workOutMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workOutMapper.fromId(null)).isNull();
    }
}
