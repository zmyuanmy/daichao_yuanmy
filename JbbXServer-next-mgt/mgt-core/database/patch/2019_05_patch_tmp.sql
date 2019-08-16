alter table tmp_funnel_user_init add index i_tmp_funnel_user_init_cookie_id (cookie_id);
alter table tmp_funnel_user_init add index i_tmp_funnel_user_init_cdate (creation_date);
alter table tmp_funnel_user_submit add index i_tmp_funnel_user_submit_uid (user_id);
alter table tmp_funnel_user_submit add index i_tmp_funnel_user_submit_cdate (creation_date);

alter table tmp_funnel_user_addmore_init add index i_tmp_funnel_user_addmore_init_uid (user_id);
alter table tmp_funnel_user_addmore_init add index i_tmp_funnel_user_addmore_init_cdate (creation_date);

alter table tmp_funnel_user_addmore_submit add index i_tmp_funnel_user_addmore_submit_uid (user_id);
alter table tmp_funnel_user_addmore_submit add index i_tmp_funnel_user_addmore_submit_cdate (creation_date);

alter table tmp_funnel_user_success_init add index i_tmp_funnel_user_success_init_uid (user_id);
alter table tmp_funnel_user_success_init add index i_tmp_funnel_user_success_init_cdate (creation_date);

alter table tmp_funnel_user_success_download add index i_tmp_funnel_user_success_download_uid (user_id);
alter table tmp_funnel_user_success_download add index i_tmp_funnel_user_success_download_cdate (creation_date);

-- tmp_app_logins

alter table tmp_app_logins add index i_tmp_app_logins_uid (user_id);
alter table tmp_app_logins add index i_tmp_app_logins_cdate (creation_date);
