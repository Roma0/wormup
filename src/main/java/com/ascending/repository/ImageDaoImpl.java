package com.ascending.repository;

import com.ascending.model.Image;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao{
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Image saveOrUpdate(Image image){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(image);
            transaction.commit();
            logger.debug("The image was saved into database successfully!");
            return image;
        }catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to save image into database.", e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Image image) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            image = session.find(Image.class, image.getId());
            session.delete(image);
            transaction.commit();
            session.close();
            logger.debug("The image was deleted.");
            return true;
        }catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error("Failure to delete the image.", e.getMessage());
        }
        return false;
    }

    @Override
    public List<Image> getImagesByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()){
            return session.createCriteria(Image.class)
                    .add(Restrictions.eq("user.id", userId))
                    .list();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Image> getImagesByBucket(String bucket) {
        String hql = "FROM Image as i WHERE i.bucket = :bucket";
        try (Session session = sessionFactory.openSession()){
            Query<Image> query = session.createQuery(hql);
            query.setParameter("bucket", bucket);
            return query.getResultList();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
